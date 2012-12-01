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
		int counter=0;
		boolean exists;
		FileWriter fstream=null;
		BufferedWriter out=null;
		
		
		try {
//			fstream = new FileWriter("sentimentanalysis.txt");
//			out = new BufferedWriter(fstream);
                
			while(((temp =reader.readLine()) != null)&&(counter<50)){
				exists=true;
				try{
					status =  this.twitter.showStatus(Long.parseLong(temp.split("\t")[1]));
				}
				catch(TwitterException te){
					exists=false;
				}
				finally{
					if(exists){
						System.out.println("\n"+status.getUser().getName() + " " + status.getText());
						System.out.println(status.getCreatedAt());
						/*kapou edw pairnoume to status.getText() kai prepei na apofasisoume
						 * ti einai (thetiko,arnhtiko,oudetero) kai auto me diko mas kritirio
						 *  
						 */
						
						//out.write(temp.split("\t")[0]+"\t"+temp.split("\t")[1]+"\t"+result);
						counter++;
					}	
				}
			}		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
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
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
