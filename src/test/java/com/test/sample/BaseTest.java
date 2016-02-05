package com.test.sample;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseTest extends TestListenerAdapter {
    public Process p;
    HashMap appiumServerProcess = new HashMap();
    public ArrayList<String> deviceUDIDiOS = new ArrayList<String>();
    public CommandPrompt commandPrompt = new CommandPrompt();
    private int thread_device_count;
    public String appiumServerPort;
    public AppiumDriver<MobileElement> driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();
    
    @BeforeClass
    public void setUp() throws Exception {
        System.out.println("In BeforeClass");
        appiumServerPort = runAppiumServer();
       
    }

    @BeforeMethod
    public void beforeMethod() throws MalformedURLException {
        if(driver==null){
            try {
				iosNative(appiumServerPort);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        ExtentTestManager.startTest(getClass().getName(), "This is a simple test.")
                .assignCategory(Thread.currentThread().getName());
        
    }
    
    public AppiumDriver<MobileElement> getDriver() {
    	System.out.println("In ");
		return driver;
	}

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.isSuccess()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, result.getMethod().getMethodName());

        } else if (result.getStatus() == ITestResult.FAILURE) {
            //ExtentTestManager.getTest().log(LogStatus.FAIL, "<pre>" + getStackTrace(result.getThrowable()) + "</pre>");
            ExtentTestManager
                    .getTest()
                    .log(
                            LogStatus.FAIL,
                            result.getMethod().getMethodName(),
                            result.getThrowable()
                    );
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, result.getMethod().getMethodName());
        }
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
//        driver.closeApp();
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }


    public void logs(String message) {
        ExtentTestManager.logOutput(message);
    }

    @AfterClass
    public void closeReport() throws IOException, InterruptedException {
        System.out.println("In After Class");
//        killAppiumServer();
    }


    public String runAppiumServer() throws Exception {
        AvailabelPorts ap = new AvailabelPorts();
        getIOSUDID();
        String port = ap.getPort();
        String chromePort = ap.getPort();
        String bootstrapPort = ap.getPort();
        String projectRoot=System.getProperty("user.dir")+"/target/";
        thread_device_count = Integer.valueOf(Thread.currentThread().getName().split("-")[3]) - 1;
//        thread_device_count = Integer.valueOf(Thread.currentThread().getName().split("-")[3]);
        System.out.println(Thread.currentThread().getName());
        System.out.println(thread_device_count);
        String UDID = deviceUDIDiOS.get(thread_device_count);
        System.out.println("UDID:"+UDID);
        String command = "appium -p " + port + " -U " + UDID + " --native-instruments-lib " + " --tmp " + projectRoot+"tmp_" + port;
        System.out.println(command);
        p = Runtime.getRuntime().exec(command);
        Thread.sleep(5000);
        appiumServerProcess.put(Thread.currentThread().getId(), getPid(p));
        return port;
    }


    public int getPid(Process process) {

        try {
        Class<?> cProcessImpl = process.getClass();
        Field fPid = cProcessImpl.getDeclaredField("pid");
        if (!fPid.isAccessible()) {
            fPid.setAccessible(true);
        }
        return fPid.getInt(process);
    } catch (Exception e) {
        return -1;
    }
}


    public void killAppiumServer() throws IOException, InterruptedException {
        if (appiumServerProcess.get(Thread.currentThread().getId()) != "-1") {
            String command = "kill -9 " + appiumServerProcess.get(Thread.currentThread().getId());
            System.out.println("******************" + command);
            Runtime.getRuntime().exec(command);
        }


    }

    public ArrayList<String> getIOSUDID() throws InterruptedException {
        try {
            String getIOSDeviceID = commandPrompt.runCommand("idevice_id --list");
            String[] lines = getIOSDeviceID.split("\n");
            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i].replaceAll("\\s+", "");
                deviceUDIDiOS.add(lines[i]);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return deviceUDIDiOS;
    }

    public void iosNative(String port) throws MalformedURLException, InterruptedException {
        // TODO Auto-generated method stub
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
        capabilities.setCapability(MobileCapabilityType.APP, "");
        capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true);
        capabilities.setCapability("bundleId", "");
        capabilities.setCapability("autoAcceptAlerts", true);
        System.out.println("http://127.0.0.1:"+port+"/wd/hub");
        Thread.sleep(5000L);
        driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:"+port+"/wd/hub"),capabilities);
        Thread.sleep(2000L);
    }
}
