package com.test.sample;

import org.openqa.selenium.By;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class SampleTest extends BaseTest {

	@Test
	public void testMethodFive_5() throws Exception {
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
//		Thread.sleep(3000);
//		getDriver().findElement(By.xpath("//*[@name='action_bar_up_navigation'][1]")).click();
//		Thread.sleep(3000);
//		getDriver().findElement(By.xpath("//*[@name='Store locat']")).click();
//		Thread.sleep(3000);
		AssertJUnit.assertTrue(true);
	}
	
	@Test
	public void testApp1(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		
		AssertJUnit.assertTrue(true);
	}
	
	@Test
	public void testApp2(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		
		AssertJUnit.assertTrue(true);
	}
	
	
	@Test
	public void testApp3(){
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		
		AssertJUnit.assertTrue(true);
	}
}
