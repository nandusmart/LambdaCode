package com.hex.challenger.watson;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
public class LambdaFunctionHandler implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);
		PersonalityInsightService service = new PersonalityInsightService();

		String str = service.getInsights(input);
		
		return str;
    }

}
