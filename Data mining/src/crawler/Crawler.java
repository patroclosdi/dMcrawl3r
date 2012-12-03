package crawler;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
//import java.sql.Date;



public class Crawler {

	
	public static void main(String[] args) throws Exception{
	
		//Consumers Key and Secret for verification
		String consumerkey = "9lLzthA7K6NvcoUIpGI40g";
		String consumersecret = "qmUCMLcb7nNztsVm0dBq3aWifEbGWWfFsNltuYK8VQ";		
		Twitter twitter;

		
		twitter = new TwitterFactory().getInstance(); // Initialize twitter Object
		twitter.setOAuthConsumer(consumerkey, consumersecret); //get verification
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
		while(accessToken==null){
		
			System.out.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			String pin = br.readLine();
			try{
				if(pin.length() > 0){
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				}
				else{
					accessToken = twitter.getOAuthAccessToken();
				}
			}
			catch (TwitterException te) {	
				if(401 == te.getStatusCode()){
					System.out.println("Unable to get the access token.");
				}else{
					te.printStackTrace();
				}
			}
		}
		
		
		br.close();
		
		SentimentAnalysis parser = new SentimentAnalysis(twitter, "selectionTweets.txt");
		parser.parse();
		parser.closeresources();
		
		FeatureExctraction export = new FeatureExctraction(twitter, "selectionTweets.txt", "sentimentanalysis.txt");
		export.getFeaturesAttributes();
		export.closeresources();
		
	}
}