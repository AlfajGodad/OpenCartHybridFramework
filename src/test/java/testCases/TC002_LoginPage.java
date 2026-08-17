package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginPage extends BaseClass{
	@Test(groups= {"Regression","Master"})
	public void loginTest() {
		try {
		HomePage hp= new HomePage(driver);
		logger.info("*** Test TC002_LoginPage started ***");
		hp.clickMyAccount();
		hp.clickLogin();
		LoginPage lp= new LoginPage(driver);
		logger.info("Entering the credentials");
		lp.putUsername(p.getProperty("userName"));
		lp.putPassword(p.getProperty("password"));
		lp.clickLogin();
		MyAccountPage myAcc= new MyAccountPage(driver);
		String msg= myAcc.getMyAccount();
		//String msg1=BaseClass.screenCapture("NewImg");
		//System.out.println(msg1);
		Assert.assertEquals(msg, "My Account");
		logger.info("*** Test TC002_LoginPage Ended ***");
		}catch(Exception e) {
			Assert.fail();
		}
	}

}
