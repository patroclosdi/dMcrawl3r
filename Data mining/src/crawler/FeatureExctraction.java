package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;


public class FeatureExctraction {
	
	private Twitter twitter;
	private BufferedReader firstfile;
	private BufferedReader positiveEmotFile;
	private BufferedReader negativeEmotFile;
	private BufferedReader positiveWordFile;
	private BufferedReader negativeWordFile;
	private BufferedReader denialWordFile;
	
	private ArrayList<String> positiveEmotList;
	private ArrayList<String> negativeEmotList;
	private ArrayList<String> positiveWordList;
	private ArrayList<String> negativeWordList;
	private ArrayList<String> denialWordList;
	
	public FeatureExctraction(Twitter twitter,String fl1){
	
		this.twitter=twitter;
		FileInputStream file=null;
		try {
			file = new FileInputStream(fl1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream stream = new DataInputStream(file);
		this.firstfile = new BufferedReader(new InputStreamReader(stream));
		
		listInitialization();	
	}
	
	private void listInitialization() {
		FileInputStream file = null;
		DataInputStream stream;
		String emot;
		String word;
		
		// open the file with the positive emoticons
		file = null;
		try {
			file = new FileInputStream("./positiveEmoticons");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stream = new DataInputStream(file);
		this.positiveEmotFile = new BufferedReader(new InputStreamReader(stream));
		
		positiveEmotList = new ArrayList<String>();
		try {
			// read every emoticon
			while ((emot = positiveEmotFile.readLine()) != null) {
			// and put it in the list
			positiveEmotList.add(emot);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// open the file with the negative emoticons
		file = null;
		try {
			file = new FileInputStream("./negativeEmoticons");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stream = new DataInputStream(file);
		this.negativeEmotFile = new BufferedReader(new InputStreamReader(stream));
		
		negativeEmotList = new ArrayList<String>();
		try {
			// read every emoticon
			while ((emot = negativeEmotFile.readLine()) != null) {
			// and put it in the list
			negativeEmotList.add(emot);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// open the file with the positive words
		//http://www.psychpage.com/learning/library/assess/feelings.html
		file = null;
		try {
			file = new FileInputStream("./positiveWords");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stream = new DataInputStream(file);
		this.positiveWordFile= new BufferedReader(new InputStreamReader(stream));
		
		positiveWordList = new ArrayList<String>();
		try {
			// read every word
			while ((word = positiveWordFile.readLine()) != null) {
			// and put it in the list
			positiveWordList.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// open the file with the negative words
		//http://www.enchantedlearning.com/wordlist/negativewords.shtml
		file = null;
		try {
			file = new FileInputStream("./negativeWords");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stream = new DataInputStream(file);
		this.negativeWordFile= new BufferedReader(new InputStreamReader(stream));
		
		negativeWordList = new ArrayList<String>();
		try {
			// read every word
			while ((word = negativeWordFile.readLine()) != null) {
			// and put it in the list
			negativeWordList.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		file = null;
		try {
			file = new FileInputStream("./denial");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stream = new DataInputStream(file);
		this.denialWordFile= new BufferedReader(new InputStreamReader(stream));
		
		denialWordList = new ArrayList<String>();
		try {
			// read every word
			while ((word = denialWordFile.readLine()) != null) {
			// and put it in the list
			denialWordList.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		
	}
	
	private int checkFor(String name, String tweet) {
		int i;
		int founds = 0;
		String word;
		String[] numb;
		
		switch(name) {	
			case ("positiveEmoticons"):
				// for every emoticon in the list
				for (i = 0; i != positiveEmotList.size(); ++i) {
					// TODO: check startsWith or endsWith also
					word = positiveEmotList.get(i);
					word = makeOutRegex(word);
					// split the tweet around emo
					numb = tweet.split(word);
					// the number of emo found is numb.lenght - 1
					if (numb.length > 0) founds += numb.length - 1;
				}
				break;
			case ("negativeEmoticons"):
				for (i = 0; i != negativeEmotList.size(); ++i) {
					word = negativeEmotList.get(i);
					word = makeOutRegex(word);
					numb = tweet.split(word);
					if (numb.length > 0) founds += numb.length - 1;
				}
				break;
			case ("positiveWords"):
				for (i = 0; i != positiveWordList.size(); ++i) {
					word = positiveWordList.get(i);
					word = makeOutRegex(word);
					numb = tweet.split(word);
					if (numb.length > 0) founds += numb.length - 1;
				}
				break;
			case ("negativeWords"):
				for (i = 0; i != negativeWordList.size(); ++i) {
					word = negativeWordList.get(i);
					word = makeOutRegex(word);
					numb = tweet.split(word);
					if (numb.length > 0) founds += numb.length - 1;
				}
				break;
			case ("denialWords"):
				for (i = 0; i != denialWordList.size(); ++i) {
					word = denialWordList.get(i);
					word = makeOutRegex(word);
					numb = tweet.split(word);
					if (numb.length > 0) founds += numb.length - 1;
				}
				break;
				
		}
		return founds;
	}
	
	
	private String makeOutRegex(String regex) {
		String[] characters = {"\\", "[", "^", "$", ".", "|", "?", "*", "+", "(", ")", "{", "}" };
		String newRegex;

		newRegex = regex;
		for (int i = 0; i != characters.length; ++i) {
			if (newRegex.contains(characters[i])) {
				newRegex = newRegex.replace(characters[i], "\\" + characters[i]);
			}
		}
		return newRegex;
	}
	
	
	public void getFeaturesAttributes(){
	
		FileWriter fstream=null;
		BufferedWriter out=null;
		boolean exists;
		Status status=null;
		String tweet = null;
		char cur;
		String comma = ",";
		String[] dok;
		
		int exclMarks, questMarks, lowercase, uppercase, quotMarks;
		
		try {
			fstream = new FileWriter("twitter.arff");
			out = new BufferedWriter(fstream);
			out.write("@RELATION twitter\n\n");
			out.write("@ATTRIBUTE tweetID\t NUMERIC\n");
			out.write("@ATTRIBUTE userID\t NUMERIC\n");
			out.write("@ATTRIBUTE followers\t NUMERIC\n");
			out.write("@ATTRIBUTE following\t NUMERIC\n");
			out.write("@ATTRIBUTE timestamp\t DATE \"EEE MMM dd HH:mm:ss zzzz yyyy\"\n");
			out.write("@ATTRIBUTE userReference\t NUMERIC\n");
			out.write("@ATTRIBUTE links\t NUMERIC\n");
			out.write("@ATTRIBUTE positiveWords\t NUMERIC\n");
			out.write("@ATTRIBUTE negativeWords\t NUMERIC\n");
			out.write("@ATTRIBUTE denials\t NUMERIC\n");
			out.write("@ATTRIBUTE exclamationMarks\t NUMERIC\n");
			out.write("@ATTRIBUTE questionMarks\t NUMERIC\n");
			out.write("@ATTRIBUTE quotationMarks\t NUMERIC\n");
			out.write("@ATTRIBUTE retweets\t NUMERIC\n");	
			out.write("@ATTRIBUTE uperCaseLetters\t NUMERIC\n");
			out.write("@ATTRIBUTE lowerCaseLettrers\t NUMERIC\n");
			out.write("@ATTRIBUTE positiveEmotions\t NUMERIC\n");
			out.write("@ATTRIBUTE negativeEmotions\t NUMERIC\n");
			out.write("@ATTRIBUTE class\t {-1, 0, 1}\n");
			out.write("\n@DATA\n");	
			
			
			String text=null,temp=null;
			int c = 1;

			while((temp=firstfile.readLine()) != null && c<=150){
				
				exists=true;
				try{
					status = this.twitter.showStatus(Long.parseLong(temp.split("\t")[1]));
				}
				catch(TwitterException te){
					exists=false;
				}
				finally{
					if(exists){
		            	c++;
						exclMarks = questMarks = lowercase = uppercase = quotMarks = 0;
						tweet = status.getText();
			
						for (int i = 0; i != tweet.length(); ++i) {
							cur = tweet.charAt(i);
							if (cur == '!') exclMarks++;
							else if (cur == '?') questMarks++;
							else if (cur == '\'' || cur == '"') quotMarks++;
							else if (cur > 'a' && cur < 'z') lowercase++;
							else if (cur > 'A' && cur < 'Z') uppercase++;
						}
						
						//save     TweetID,UserID
						text=temp.split("\t")[1]+ comma +temp.split("\t")[0] + comma;
						
						//save followers,following
						text=text+status.getUser().getFriendsCount() + comma + status.getUser().getFollowersCount() + comma;
						
						//timestamp
						text = text + "\"" + status.getCreatedAt() + "\"" + comma;
						
						// user reference
						String temp2[]=status.getText().split("@");
						int counter=0;
						for (int i = 1; i < temp2.length; i++ ) {
							if ((temp2[i].charAt(0) >= 'a' && temp2[i].charAt(0) <= 'z') || (temp2[i].charAt(0) >= 'A' && temp2[i].charAt(0) <= 'Z') || temp2[i].charAt(0) >= '0' && temp2[i].charAt(0) <= '9' ){ 
								counter++;
							}	
						}
						text = text + Integer.toString(counter) + comma;
						
						// hyperlinks
						temp2 = null;
						temp2 = status.getText().split("http");
						text = text + Integer.toString(temp2.length-1) + comma;
						
						
						// positive words
						text = text + Integer.toString(checkFor("positiveWords", tweet)) + comma;
						
						// negative words
						text = text + Integer.toString(checkFor("negativeWords", tweet)) + comma;
						
						// denials
						text = text + Integer.toString(checkFor("denialWords", tweet)) + comma;
						
						// exclamations marks
						text = text + Integer.toString(exclMarks) + comma;
						
						// question marks
						text = text + Integer.toString(questMarks) + comma;
						
						// quotation marks
						text = text + Integer.toString(quotMarks/2) + comma;
						
						// retweets
						text = text + status.getRetweetCount() + comma;
						
						// uppercase latters
						text = text + Integer.toString(uppercase) + comma;
						
						// lowercase letters
						text = text + Integer.toString(lowercase) + comma;
						
						// positive emoticons
						text = text + Integer.toString(checkFor("positiveEmoticons", tweet)) + comma;
						
						// negative emoticons
						text = text + Integer.toString(checkFor("negativeEmoticons", tweet)) + comma;
						text = text + Integer.parseInt(temp.split("\t")[2]) + "\n"; 
						out.write(text);
//						System.out.println("counter: "+c+"   text: " + tweet);						
//						System.out.println("id: " + status.getId());
//						System.out.println();
//			            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
//			            String password = stdin.readLine();
//			            if(!password.equals("5")){
//			            	text = text + Integer.parseInt(password) + "\n";		            	
//			            	out.write(text);
//			            }	
					}
			}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			 if(out != null) {
				 try {
					 out.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
			
			 if(fstream != null) {
				 try {
					 fstream.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		}	
	}
	
	
	
	public void closeresources(){
		try {
			firstfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}