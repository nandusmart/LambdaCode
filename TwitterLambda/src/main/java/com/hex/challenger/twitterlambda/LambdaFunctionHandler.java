package com.hex.challenger.twitterlambda;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hex.challenger.twitterlambda.dao.Tweet;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class LambdaFunctionHandler implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);
        
        ObjectMapper mapper2 = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper2.writeValueAsString(getAllTweets(input).getUserMessages());
			System.out.println("Twitter Text: " + jsonInString);
	        
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        // TODO: implement your handler
        return jsonInString;
    }
    
    public Tweet getAllTweets(String username)
	{
    	ConfigurationBuilder configBuilder = new ConfigurationBuilder().setDebugEnabled(true)
    			  .setOAuthConsumerKey("qWHs2ZMrs4CGG906CfbyImZmF")
    			  .setOAuthConsumerSecret("3eTXTXZC6fpCLJzpGubWWcY056LUk72Jxz7c5GNuYkS1J3vCpY")
    			  .setOAuthAccessToken("1062167800373014530-32zo3GTzwLj7AjVXaWRYA6ofP9vjiz")
    			  .setOAuthAccessTokenSecret("oAGnvnRBcwKHkHrEsKUQ9aYonDpJfHhazJX0Cv0pYJlm9");
    			
    			TwitterFactory tf = new TwitterFactory(configBuilder.build());
    			Twitter twitter = tf.getInstance();
    			
		String tweetMessage=new String();
		Tweet tweets=new Tweet();
		try
		{
			Query query = new Query("from:"+username);
			query.setCount(100);
			QueryResult result;
			//do {
                result = twitter.search(query);
                List<Status> pulledTweets = result.getTweets();
                for (Status tweet : pulledTweets) {
                	tweetMessage=tweetMessage+" "+tweet.getText().replaceAll("[\\S]+://[\\S]+", "");
                	
                }
            //} while ((query = result.nextQuery()) != null);
			
		 tweetMessage=tweetMessage.replaceAll("[^A-Za-z0-9 ]", " ");
		 tweetMessage=tweetMessage.replaceAll("( )+", " ");
			tweets.setUserId(username);
			tweets.setUserMessages(tweetMessage);
			
		}
		catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        } 
		
		return tweets;
	}
	

}
