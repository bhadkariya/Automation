package common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG extends ExtWebDrivers {

	public static ExtentReports extent = new ExtentReports();

	public ExtentReports getReportObject() {

		ExtWebDrivers ewd = new ExtWebDrivers();
		try {
			ewd.teaminfo();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		String ExtentReportspaths = System.getProperty("user.dir") + EextentReportsPath
				+ this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1) + "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".html";

		System.out.println("path of extent report is ::" + ExtentReportspaths);
		ExtentSparkReporter reporter = new ExtentSparkReporter(ExtentReportspaths);		
		reporter.config().setDocumentTitle(documentTitles);
		reporter.config().setReportName(EextentReportsName);
		extent.attachReporter(reporter);

		//extent.setSystemInfo("qa", "suri");
		
		
		
		// Capabilities cap = ((RemoteWebDriver) getDriver()).getCapabilities();
		  //  String v = cap.getBrowserVersion().toString();
		    
		  //  extent.setSystemInfo("Browser Name",((RemoteWebDriver) getDriver()).getCapabilities().getBrowserVersion().toString());
			extent.setSystemInfo("Environment Name",EnvironmentName);
			extent.setSystemInfo("Eextent Reports Name",EextentReportsName);
			
		
		
		return extent;
	}

}
