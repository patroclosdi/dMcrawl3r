package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;



public class ExportFeatures {

	Twitter twitter;
	BufferedReader firstfile;
	BufferedReader secondfile;
	

	
	public ExportFeatures(Twitter twitter,String fl1,String fl2){

		this.twitter=twitter;
		FileInputStream file=null;
		try {
			file = new FileInputStream(fl1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream stream = new DataInputStream(file);
		this.firstfile = new BufferedReader(new InputStreamReader(stream));

		file=null;
		try {
			file = new FileInputStream(fl2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stream = new DataInputStream(file);
		this.secondfile = new BufferedReader(new InputStreamReader(stream));
	}
	
	
	public void getFeaturesAttributes(){

		FileWriter fstream=null;
		BufferedWriter out=null;
		boolean exists;
		Status status=null;
		
		try {
			fstream = new FileWriter("twitter.arff");
			out = new BufferedWriter(fstream);
			out.write("@RELATION twitter\n\n");
			out.write("@ATTRIBUTE tweetID  NUMERIC\n");
			out.write("@ATTRIBUTE userID  NUMERIC\n");
			out.write("@ATTRIBUTE followers  NUMERIC\n");
			out.write("@ATTRIBUTE following  NUMERIC\n");
			out.write("@ATTRIBUTE timestamp  DATE \"yyyy-MM-EE dd HH:mm:ss\"\n");
			out.write("@ATTRIBUTE userReference  NUMERIC\n");
			out.write("@ATTRIBUTE links  NUMERIC\n");
			out.write("@ATTRIBUTE positiveWords  NUMERIC\n");
			out.write("@ATTRIBUTE negativeWords  NUMERIC\n");
			out.write("@ATTRIBUTE denials  NUMERIC\n");
			out.write("@ATTRIBUTE exclamationMarks  NUMERIC\n");
			out.write("@ATTRIBUTE questionMarks  NUMERIC\n");
			out.write("@ATTRIBUTE quotationMarks  NUMERIC\n");
			out.write("@ATTRIBUTE retweets  NUMERIC\n");
			out.write("@ATTRIBUTE uperCaseLetters  NUMERIC\n");
			out.write("@ATTRIBUTE lowerCaseLettrers  NUMERIC\n");
			out.write("@ATTRIBUTE positiveEmotions  NUMERIC\n");
			out.write("@ATTRIBUTE negativeEmotions  NUMERIC\n");
			out.write("@DATA\n");
			
			
			String text=null,temp=null;
			while((temp=firstfile.readLine()) != null){
				exists=true;
				try{
					status =  this.twitter.showStatus(Long.parseLong(temp.split("\t")[1]));
				}
				catch(TwitterException te){
					exists=false;
				}
				finally{
					if(exists){
						try {
							//save TweetID,UserID
							text=temp.split("\t")[1]+","+temp.split("\t")[0]+",";
	
							//save followers,following
							text=text+status.getUser().getFriendsCount()+","+status.getUser().getFollowersCount()+",";
	
							//timestamp
							
							String timestamp=status.getCreatedAt().toString();
							SimpleDateFormat sdtSource = new SimpleDateFormat("EE MM dd dd HH:mm:ss z yyyy");							
							Date parse = sdtSource.parse(timestamp);
							SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-EE dd HH:mm:ss");						     
						    //parse the date into another format
						    timestamp = sdfDestination.format(parse);
						    text=text+"\""+timestamp+"\""+",";
						    
							
							
							
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}	
			}
		
		
		
		
		
		
		
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
//			if(fstream != null) {
//				try {
//					fstream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if(out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}		
	}
	
	
	
	public void closeresources(){
		try {
			firstfile.close();
			secondfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
