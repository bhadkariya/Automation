package common.login.pages;

import java.util.HashMap;

import common.utils.ExtWebDrivers;
import dev.failsafe.internal.util.Assert;

public class LoginTestPage {
	
	public static String Username = null;
	public static String Password = null; 
	public static String AssertMessage = null; 

	
	public void LoginforFacebook(ExtWebDrivers ewd) {
		
	//	static Logger log = Logger.getLogger(LoginforFacebook.class);
	
		ewd.openURL(ewd.getFacebookUrlName());		
		System.out.println("Username::::::::::::::" + Username);
		System.out.println("Password::::::::::::::" + Password);		
		ewd.wdSendKey("//input[@name='uid']", Username);
		//extentTest.get().log(Status.INFO, "username Insert");
		ewd.reportComment("user");
		ewd.wdSendKey("//input[@name='password']", Password);
		ewd.wdClick("//input[@name='btnLogin']");
		String expectedMessage = AssertMessage;
		String message =	ewd.wdassertmessage("//marquee[@class=\"heading3\"]");
		Assert.isTrue(true, expectedMessage, message);
		System.out.println(expectedMessage  +  message);
	
			}
	
	public void setPageParameterValues(HashMap<String, String> parms) {
		this.Username = parms.get("Username");
		this.Password = parms.get("Password");
		this.AssertMessage = parms.get("AssertMessage");
		
		
	}

}
