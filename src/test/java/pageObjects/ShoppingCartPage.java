package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#content h1")
    private WebElement shoppingCartHeading;

    @FindBy(css = ".table-responsive tbody tr")
    private List<WebElement> cartRows;

    @FindBy(css = ".table-responsive tbody tr td:nth-child(2) a")
    private List<WebElement> productNames;


    public String getShoppingCartHeading() {

        return getText(
                shoppingCartHeading
        );
    }


    public boolean isProductPresent(
            String expectedProduct) {

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
}
