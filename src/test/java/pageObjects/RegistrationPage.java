package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-firstname")
    private WebElement txtFirstName;

    @FindBy(id = "input-lastname")
    private WebElement txtLastName;

    @FindBy(id = "input-email")
    private WebElement txtEmail;

    @FindBy(id = "input-telephone")
    private WebElement txtTelephone;

    @FindBy(id = "input-password")
    private WebElement txtPassword;

    @FindBy(id = "input-confirm")
    private WebElement txtConfirmPassword;

    @FindBy(name = "agree")
    private WebElement chkPrivacyPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement btnContinue;

    public void setFirstName(String firstName) {
        type(txtFirstName, firstName);
    }

    public void setLastName(String lastName) {
        type(txtLastName, lastName);
    }

    public void setEmail(String email) {
        type(txtEmail, email);
    }

    public void setTelephone(String telephone) {
        type(txtTelephone, telephone);
    }

    public void setPassword(String password) {
        type(txtPassword, password);
    }

    public void setConfirmPassword(String password) {
        type(txtConfirmPassword, password);
    }

    public void acceptPrivacyPolicy() {
        click(chkPrivacyPolicy);
    }

    public void clickContinue() {
        click(btnContinue);
    }
}