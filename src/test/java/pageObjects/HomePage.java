package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[text()='My Account']")
    private WebElement myAccount;

    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(linkText = "Login")
    private WebElement loginLink;
    
    @FindBy(name = "search")
    private WebElement txtSearch;

    @FindBy(css = "button.btn.btn-default.btn-lg")
    private WebElement btnSearch;

    public void clickMyAccount() {
        click(myAccount);
    }

    public void clickRegister() {
        click(registerLink);
    }

    public void clickLogin() {
        click(loginLink);
    }
    
    public void enterSearchProduct(String productName) {
        type(txtSearch, productName);
    }

    public void clickSearch() {
        click(btnSearch);
    }
    
    public boolean isLoginDisplayed() {
        return isDisplayed(loginLink);
    }
}