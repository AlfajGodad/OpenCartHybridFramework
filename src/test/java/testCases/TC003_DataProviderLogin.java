package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utility.DataProvider1;

public class TC003_DataProviderLogin extends BaseClass{
	
	
	
	@Test(dataProvider = "LoginData1", dataProviderClass = DataProvider1.class, groups= {"DataDriven"})
	public void loginTest(String username, String password) {
		HomePage hp= new HomePage(driver);
		try {
		
		logger.info("*** Test TC002_LoginPage started ***");
		hp.clickMyAccount();
		hp.clickLogin();
		LoginPage lp= new LoginPage(driver);
		logger.info("Entering the credentials");
		lp.putUsername(username);
		lp.putPassword(password);
		lp.clickLogin();
		MyAccountPage myAcc= new MyAccountPage(driver);
		String msg= myAcc.getMyAccount();
		if(myAcc.txtMyAccount.isDisplayed()) {
			Assert.assertEquals(msg, "My Account");
			logger.info("Test is passed");
			myAcc.clickLogout();
		}
		
		logger.info("*** Test TC002_LoginPage Ended ***");
		}catch(Exception e) {
			Assert.fail();
		}
	}
}
