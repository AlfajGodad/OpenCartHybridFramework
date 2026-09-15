package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".product-thumb h4 a")
    private List<WebElement> productNames;

    @FindBy(css = "#content h1")
    private WebElement searchHeading;
    
    @FindBy(css = ".alert-success")
    private WebElement successMessage;
    
    @FindBy(linkText = "shopping cart")
    private WebElement shoppingCartLink;
    
    


    public String getSearchHeading() {
        return getText(searchHeading);
    }
    
    


    public boolean isProductDisplayed(String expectedProduct) {

        for (WebElement product : productNames) {

            String actualProduct =
                    product.getText().trim();

            if (actualProduct.equalsIgnoreCase(
                    expectedProduct)) {

                return true;
            }
        }

        return false;
    }
    
    public void addProductToCart(String productName) {

        List<WebElement> products =
                driver.findElements(
                        By.cssSelector(".product-layout")
                );

        for (WebElement product : products) {

            String name =
                    product.findElement(
                            By.cssSelector("h4 a")
                    ).getText().trim();

            if (name.equalsIgnoreCase(productName)) {

                WebElement addToCartButton =
                        product.findElement(
                                By.cssSelector(
                                        "button[onclick*='cart.add']"
                                )
                        );

                click(addToCartButton);

                return;
            }
        }

        throw new NoSuchElementException(
                "Product not found: " + productName
        );
    }
    
    public String getSuccessMessage() {
        return getText(successMessage);
    }
    
    public void openShoppingCart() {
        click(shoppingCartLink);
    }
}