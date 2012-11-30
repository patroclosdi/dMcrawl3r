import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;



public class Crawler {


	public static void main(String[] args) throws Exception{
		String[] recipientId = { "127964415", "114119906","413333507"};
		String consumerkey = "P859ZLz5tKOmKlxTogCitA";
		String consumersecret = "wjp3jmDan9EnKY3by4fgUd1fd7XIZLljGkbgrYfdgA "; 
		
		Twitter twitter;
		
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerkey, consumersecret);
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
	           }else{
	             accessToken = twitter.getOAuthAccessToken();
	           }
	        } catch (TwitterException te) {	        	
	          if(401 == te.getStatusCode()){
	            System.out.println("Unable to get the access token.");
	          }else{
	            te.printStackTrace();
	          }
	        }
		}
		
	      System.out.println(twitter.verifyCredentials().getId());
	      System.out.println("token : " + accessToken.getToken());
	      System.out.println("tokenSecret : " + accessToken.getTokenSecret());

	      
	      DirectMessage message=twitter.showDirectMessage(127964415);
	      System.out.println("From: @" + message.getSenderScreenName() + " " + message.getText());
		//twitter.sendDirectMessage(recipientId, arg1);
		
		
	}
}
