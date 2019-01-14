package com.hex.challenger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<String, String> {
	String url = "https://68f5irtq5k.execute-api.us-east-1.amazonaws.com";
    @Override
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
    	String tweets = handler.callTweet("cnbc");
    	String personality = handler.callPersonality(tweets);
        return personality;
    }
    
    private String callTweet(String input) {
    	String finalTweets = "";
    	try {
    		Client client = ClientBuilder.newClient();
			WebTarget target = client.target(url);
			String str = "\"" + input + "\"";
	        finalTweets = target.path("Production").
	                            path("twitter").
	                            request().
	                            post(Entity.json(str)).readEntity(String.class);

			//System.out.println(finalTweets);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return finalTweets;
    }
    private String callPersonality(String input) {
    	String personalityText = "";
    	try {
    		Client client = ClientBuilder.newClient();
			WebTarget target = client.target(url);
			//input = input.replaceAll("", "")
			//System.out.println(input);
	        personalityText = target.path("Production").
	                            path("watson").
	                            request().
	                            post(Entity.json(input)).readEntity(String.class);

			//System.out.println(personalityText);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return personalityText;
    }
    
//    public static void main(String args[]) {
//    	LambdaFunctionHandler handler = new LambdaFunctionHandler();
//    	String tweets = handler.callTweet("cnbc");
//    	
//    	String personality = handler.callPersonality(tweets);
//    	System.out.println(personality);
//    }
}
