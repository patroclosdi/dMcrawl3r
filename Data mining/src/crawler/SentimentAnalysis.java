package crawler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;


public class SentimentAnalysis {

	Twitter twitter;
	BufferedReader reader;
	
	public SentimentAnalysis(Twitter twitter,String fl) {
		this.twitter=twitter;
		FileInputStream file=null;
		try {
			file = new FileInputStream(fl);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream stream = new DataInputStream(file);
		this.reader = new BufferedReader(new InputStreamReader(stream));
	}
		
		
	public void parse() {
		
		String temp;
		Status status=null;
		int counter=1;
		boolean exists;
		FileWriter fstream=null;
		BufferedWriter out=null;
		
				
		int counter1=1;
		try {
			while(((temp =reader.readLine()) != null)&&(counter<150)){
				exists=true;
				try{
					status = this.twitter.showStatus(Long.parseLong(temp.split("\t")[1]));
				}
				catch(TwitterException te){
					exists=false;
				}
				finally{
					if(exists){
						System.out.println(counter+" "+status.getText() + " " + temp.split("\t")[2]+"\n");
						counter++;
					}
					counter1++;
				}
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		finally{}
	}
	
	
	
	public void closeresources(){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}