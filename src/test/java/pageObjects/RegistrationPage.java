package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage{
	
	public RegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstname;
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastname;
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtConfirmPassword;
	@FindBy(xpath="//label[normalize-space()='Yes']")
	WebElement radioNewsletter;
	@FindBy(xpath="//input[@name='agree']")
	WebElement chkPolicy;
	@FindBy(xpath="//input[@value='Continue']")
	WebElement btnContinue;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	public WebElement msgSuccess;
	
	public void setFirstName(String name) {
		txtFirstname.sendKeys(name);
	}
	public void setLastName(String Lname) {
		txtLastname.sendKeys(Lname);
	}
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}
	public void setTelephone(String num) {
		txtTelephone.sendKeys(num);
	}
	public void setPassword(String pass) {
		txtPassword.sendKeys(pass);
	}
	public void setConfirmPassword(String pass) {
		txtConfirmPassword.sendKeys(pass);
	}
	public void selectNewsLetter() {
		radioNewsletter.click();
	}
	public void checkPolicy() {
		chkPolicy.click();
	}
	public void clickContinue() {
		btnContinue.click();
	}
	public String getSuccessMsg() {
		return msgSuccess.getText();
	}

}
