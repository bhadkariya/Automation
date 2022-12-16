package common.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
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
		System.out.println("Failed");
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
			extentTest.get().addScreenCaptureFromPath(takeSnapShot(result));
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
		System.out.println("on start");
	//	extentTest.get().log(Status.WARNING, "Test WARNING");
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("on Finish");
		extent.flush();		
		//extentTest.get().log(Status.INFO, "Test Finish");
		
		
	}
	
//	public void tearDown(ITestResult result) throws Exception {
//		if (ITestResult.FAILURE == result.getStatus() || ITestResult.SKIP == result.getStatus()
//				|| ITestResult.SUCCESS_PERCENTAGE_FAILURE == result.getStatus()) {
//			System.out.println("my status:::::::::::::::::::::::::::::" + result.getStatus());
//
//			try {
//				takeSnapShot(result);
//			} catch (Exception e) {
//				System.out.println("Taking ScreenShot" + e.getMessage());
//			}
//		}
//		driver.quit();
//	}
//
//	public String takeSnapShot(ITestResult result) throws Exception {
//		TakesScreenshot driver1 = (TakesScreenshot) driver;
//		File source = driver1.getScreenshotAs(OutputType.FILE);
//		ScreenShotPaths = System.getProperty("user.dir") + ScreenShotPath
//				+ this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1) + "_"
//				+ result.getMethod().getMethodName() + "_"
//				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "_"
//				+ result.getStatus() + ".png";
//		System.out.println("ffffffffffffffffffffffffffffffffffffffffffffff" + ScreenShotPaths);
//		FileUtils.copyFile(source, new File(ScreenShotPaths));
//		return ScreenShotPaths;
//
//	}
//


}
