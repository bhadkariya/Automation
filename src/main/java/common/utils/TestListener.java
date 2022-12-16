package common.utils;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;



public class TestListener extends ExtWebDrivers implements ITestListener{
	
	//public static WebDriver driver;
	
	ExtentReporterNG extentReportNG = new ExtentReporterNG();
	ExtWebDrivers ewd = new ExtWebDrivers();
	
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	ExtentReports extent = extentReportNG.getReportObject();
	
	ExtentTest test;

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
		System.out.println("Test Start");
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
		
		 
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("success");
		extentTest.get().log(Status.PASS, "Test Pass");
		try {
			extentTest.get().addScreenCaptureFromPath(ewd.takeSnapShot(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("success");
		extentTest.get().log(Status.FAIL, "Test Fail");
		try {
			extentTest.get().addScreenCaptureFromPath(ewd.takeSnapShot(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("Skip");
		extentTest.get().log(Status.SKIP, "Test Skip");
		try {
			extentTest.get().addScreenCaptureFromPath(ewd.takeSnapShot(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("on WARNING");
	//	extentTest.get().log(Status.WARNING, "Test WARNING");
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("on Finish");
		extent.flush();		
		//extentTest.get().log(Status.INFO, "Test Finish");
		
		
	}
	
	

}
