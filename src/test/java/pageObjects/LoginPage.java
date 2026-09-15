package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-email")
    private WebElement txtEmail;

    @FindBy(id = "input-password")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Login']")
    private WebElement btnLogin;

    @FindBy(css = ".alert.alert-danger.alert-dismissible")
    private WebElement warningMessage;

    public void enterEmail(String email) {
        type(txtEmail, email);
    }

    public void enterPassword(String password) {
        type(txtPassword, password);
    }

    public void clickLogin() {
        click(btnLogin);
    }

    public String getWarningMessage() {
        return getText(warningMessage);
    }
}