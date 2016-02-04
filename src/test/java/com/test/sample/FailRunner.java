package com.test.sample;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class FailRunner {
	@Test(retryAnalyzer=Retry.class)
	public void testApp4(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		AssertJUnit.assertTrue(false);
		
	}
}
