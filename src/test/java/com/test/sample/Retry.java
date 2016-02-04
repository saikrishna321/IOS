package com.test.sample;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	private int retryCount = 0;
    private int maxRetryCount = 2;

    public boolean retry(ITestResult result) {

    	if(result.getStatus() == ITestResult.FAILURE){
    		System.out.println("Test Failed");
    		if(retryCount == maxRetryCount){
    			System.out.println("Log report");
    		}
    	     if (retryCount < maxRetryCount) {
    	            retryCount++;
    	            System.out.println("Retrying*****" + retryCount);
    	            return true;
    	       
    	        }
    	}
   
        return false;
    }
}
