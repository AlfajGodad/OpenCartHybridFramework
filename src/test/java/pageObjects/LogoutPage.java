package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage extends BasePage {

    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#content h1")
    private WebElement logoutHeading;
    
    @FindBy(xpath="//a[normalize-space()='Continue']")
    private WebElement continueButton;


    public String getLogoutHeading() {

        return getText(
                logoutHeading
        );
    }
    
    public void clickContinue() {
    	click(continueButton);
    }
}