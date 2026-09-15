package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }
    
    @FindBy(xpath="//span[normalize-space()='My Account']")
    WebElement myAccount;

    @FindBy(xpath = "//h2[text()='My Account']")
    private WebElement myAccountHeading;

    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutLink;
    


    public boolean isMyAccountDisplayed() {
        return isDisplayed(myAccountHeading);
    }

    public void clickLogout() {
        click(logoutLink);
        
    }
    
 
    
    public void clickMyAccount() {
    	click(myAccount);
    }
}