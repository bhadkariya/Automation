package common.utils;

import static common.utils.TestListener.extentTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.BuildInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.log4testng.Logger;

import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtWebDrivers {

	FileInputStream fis, fise;
	public Properties props, propse;
	public static String BrowserName, EnvironmentName, FacebookUrlName, GmailUrlName, EextentReportsPath,
			EextentReportsName, LogPath, ScreenShotPath, ScreenShotPaths, wfmReports, ExtentReportspaths,
			documentTitles, InputEnvs, InputBrowsers, InputTypeofTesting, InputTeam;
	public static WebDriver driver;
	public Logger log;
	private String testName;

	@BeforeMethod
	public void teaminfo() throws IOException {

		// String TeamName = System.getProperty("propertyTeamName");

		InputEnvs = System.getProperty("Environment");
		InputBrowsers = System.getProperty("Browser");
		InputTypeofTesting = System.getProperty("Types_of_Testing");
		InputTeam = System.getProperty("TeamName");

		System.out.println("envs::::::::::::::::::::::::::" + InputEnvs);
		System.out.println("browsers::::::::::::::::::::::::::" + InputBrowsers);
		System.out.println("typeoftesting::::::::::::::::::::::::::" + InputTypeofTesting);
		System.out.println("team::::::::::::::::::::::::::" + InputTeam);

		try {
			if (InputTeam.equals("WFM")) {
				fis = new FileInputStream("resources/Setup/wfm.properties");

			} else if (InputTeam.equals("CoreHr")) {
				fis = new FileInputStream("resources/Setup/corehr.properties");
			}
			props = new Properties();

			props.load(fis);

			EextentReportsPath = props.getProperty("config.extentReportPath");
			System.out.println("EextentReportsPath-------------" + EextentReportsPath);

			EextentReportsName = props.getProperty("config.extentReportName");
			System.out.println("EextentReportsName-------------" + EextentReportsName);

			LogPath = props.getProperty("config.logPath");
			System.out.println("LogPath-------------" + LogPath);

			ScreenShotPath = props.getProperty("config.screenShotPath");
			System.out.println("ScreenShotPath-------------" + ScreenShotPath);

			documentTitles = props.getProperty("config.reportsTitel");
			System.out.println("documentTitles-------------" + documentTitles);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (InputEnvs.equals("Staging")) {
			fise = new FileInputStream("resources/Common/staging.properties");
		} else if (InputEnvs.equals("Sohum")) {
			fise = new FileInputStream("resources/Common/sohum.properties");
		}

		propse = new Properties();
		propse.load(fise);

		FacebookUrlName = propse.getProperty("config.facebookurl");
		GmailUrlName = propse.getProperty("config.gmailurl");
	

	}

	void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void launchBrowser() throws IOException {
		if (InputBrowsers.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			setDriver(new FirefoxDriver());

		} else if (InputBrowsers.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			setDriver(new ChromeDriver());

		} else if (InputBrowsers.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			setDriver(new ChromeDriver());

		} else if (InputBrowsers.equalsIgnoreCase("Headless")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("disable-gpu");
			driver = new ChromeDriver(chromeOptions);

		}

		else {
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();	
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void setup() throws Exception {
		launchBrowser();

		BuildInfo info = new BuildInfo();
		String infoString = info.toString();
		System.out.println(infoString);

		log = Logger.getLogger(ExtWebDrivers.class);
		logSOP(testName);
	}

	@BeforeMethod(alwaysRun = true)
	public String nameBefore(Method method) {
		return this.testName = method.getName();

	}

	public void logSOP(Object testName) throws Exception {

		// String LogPaths = System.getProperty("user.dir")+ LogPath +
		// testName+"demo.log";

		String LogPaths = System.getProperty("user.dir") + LogPath + testName + "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".log";

		File file = new File(LogPaths);
		FileOutputStream fis = new FileOutputStream(file);
		PrintStream out = new PrintStream(fis);
		System.setOut(out);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static String getFacebookUrlName() {
		return FacebookUrlName;
	}

	void setFacebookUrlName(String urlheader) {
		urlheader = this.FacebookUrlName;
	}

	public void openURL(String url) {
		driver.get(url);
	}

//  public void wdclosed(){
//	  driver.close();
//	  driver.quit();
//  }

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws Exception {
		if (ITestResult.SUCCESS == result.getStatus() || ITestResult.FAILURE == result.getStatus()
				|| ITestResult.SKIP == result.getStatus()
				|| ITestResult.SUCCESS_PERCENTAGE_FAILURE == result.getStatus()) {

			try {
				takeSnapShot(result);
			} catch (Exception e) {
				System.out.println("Taking ScreenShot" + e.getMessage());
			}
		}
		driver.quit();
	}

	public String takeSnapShot(ITestResult result) throws Exception {
		TakesScreenshot driver1 = (TakesScreenshot) driver;
		File source = driver1.getScreenshotAs(OutputType.FILE);
		ScreenShotPaths = System.getProperty("user.dir") + ScreenShotPath
				+ this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1) + "_"
				+ result.getMethod().getMethodName() + "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "_"
				+ result.getStatus() + ".png";
		FileUtils.copyFile(source, new File(ScreenShotPaths));
		return ScreenShotPaths;

	}

	public void wdSendKey(String webxpath, String webinput) {
		if (webxpath.startsWith("//")) {
			driver.findElement(By.xpath(webxpath)).sendKeys(webinput);
			;
		} else {
			try {
				driver.findElement(By.xpath(webxpath)).sendKeys(webinput);
			} catch (RuntimeException e) {
				driver.findElement(By.xpath(webxpath)).sendKeys(webinput);
				e.printStackTrace();
			}
		}

	}

	public void wdClick(String Webclick) {
		if (Webclick.startsWith("//")) {
			driver.findElement(By.xpath(Webclick)).click();

		} else {
			try {
				driver.findElement(By.xpath(Webclick)).click();
			} catch (RuntimeException e) {
				driver.findElement(By.xpath(Webclick)).click();
				e.printStackTrace();

			}
		}
	}

	public String wdassertmessage(String WeMessage) {
		return driver.findElement(By.xpath(WeMessage)).getText();
	}

	public void reportComment(String recomment) {
		extentTest.get().log(Status.INFO, recomment);

	}

}