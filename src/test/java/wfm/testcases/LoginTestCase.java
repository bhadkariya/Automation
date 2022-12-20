 package wfm.testcases;

import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.login.pages.LoginTestPage;
import common.utils.ExtWebDrivers;

public class LoginTestCase extends ExtWebDrivers{
	
	private LoginTestPage logintestpage = null;
	
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception{
		super.setup();		
		HashMap<String, String> parms = new HashMap<String , String>();
		parms.put("Username", "mngr461758");
		parms.put("Password", "sAsyzYs");
		parms.put("AssertMessage", "Welcome To Manager's Page of Guru99 Bank");		
		logintestpage = new LoginTestPage();
		logintestpage.setPageParameterValues(parms);
		
						
	}
	
	@Test(alwaysRun = true)
public void facebooklogintest() throws Exception{ 
		LoginTestPage logintestpage = new LoginTestPage();
		logintestpage.LoginforFacebook(new ExtWebDrivers());	
		//extentTest.debug(MarkupHelper.createLabel(getBrowser() + " " + getVersion(), ExtentColor.TRANSPARENT));
//extentTest.get().
		
	}
	
	//@AfterMethod(alwaysRun = true)
public void Teamdown() throws Exception{ 
		//wdclosed();
}
}