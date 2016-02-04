package com.test.sample;

import com.myProject.MyClass;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class Sample2Test extends BaseTest {
	@Test
	public void testApp4(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		AssertJUnit.assertTrue(true);
		
	}
	
	@Test
	public void testApp5(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Reporter.log("Test Logger Same Under the test");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Logger Same Under the test");
		AssertJUnit.assertTrue(true);
	}

	@Test(retryAnalyzer = Retry.class)
	public void testApp6(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		MyClass c = new MyClass();
        Assert.assertEquals(c.sum(2, 3), 5);

	}


	@Test
	public void testApp7(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());

		AssertJUnit.assertTrue(true);
	}
}
