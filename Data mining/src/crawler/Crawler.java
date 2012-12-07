package crawler;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;




public class Crawler {

	
	public static void main(String[] args) throws Exception{

		//Consumers Key and Secret for verification
		String consumerkey = "9lLzthA7K6NvcoUIpGI40g";
		String consumersecret = "qmUCMLcb7nNztsVm0dBq3aWifEbGWWfFsNltuYK8VQ";		
		Twitter twitter; //create a new object

		
		twitter = new TwitterFactory().getInstance(); // Initialize twitter Object
		twitter.setOAuthConsumer(consumerkey, consumersecret); //get verification
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		
		FeatureExctraction export = new FeatureExctraction(twitter, "annotated.txt");
		export.getFeaturesAttributes();
		export.closeresources();
		
	}
}