package testCases;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class TC001_RegistrationPage extends BaseClass{
	
	@Test(groups= {"Sanity", "Master"})
	public void registrationTest() {
		HomePage hp= new HomePage(driver);
		logger.info("**** Test TC001_RegistrationPage  is Starred..****");
		try {
			
		
		hp.clickMyAccount();
		logger.info("Clicked MyAccount");
		hp.clickRegister();
		logger.info("Clicked Resgister");
		RegistrationPage rp= new RegistrationPage(driver);
		logger.info("Entering User Information");
		rp.setFirstName(randomString());
		rp.setLastName(randomString());
		rp.setEmail(randomString()+"@gmail.com");
		rp.setTelephone(randomNumber());
		String pass= randomAlphaNumeric();
		rp.setPassword(pass);
		rp.setConfirmPassword(pass);
		rp.selectNewsLetter();
		rp.checkPolicy();
		rp.clickContinue();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(rp.msgSuccess));
		String msg= rp.getSuccessMsg();
		
		Assert.assertEquals(msg, "Your Account Has Been Created!");
		} catch(Exception e) {
			logger.error("Test Failed");
			Assert.fail();
			
		}
		
		logger.info("Test is Finished");
		
	}
	
	

}
