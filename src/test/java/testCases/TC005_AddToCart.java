package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC005_AddToCart extends BaseClass {

    @Test
    public void verifyAddProductToCart() {

        logger.info(
                "Starting Add Product To Cart Test"
        );

        String productName = "MacBook";

        /*
         * Search product
         */
        HomePage homePage =
                new HomePage(driver);

        homePage.enterSearchProduct(
                productName
        );

        homePage.clickSearch();

        /*
         * Search results page
         */
        SearchPage searchPage =
                new SearchPage(driver);

        Assert.assertTrue(
                searchPage.isProductDisplayed(
                        productName
                ),
                "Product not found: "
                        + productName
        );

        logger.info(
                "Product found: "
                        + productName
        );

        /*
         * Add product to cart
         */
        searchPage.addProductToCart(
                productName
        );

        logger.info(
                "Clicked Add to Cart for: "
                        + productName
        );

        /*
         * Verify success message
         */
        String successMessage =
                searchPage.getSuccessMessage();

        logger.info(
                "Success Message: "
                        + successMessage
        );

        Assert.assertTrue(
                successMessage.contains(
                        "Success: You have added"
                ),
                "Add to cart success message was not displayed"
        );

        Assert.assertTrue(
                successMessage.contains(
                        productName
                ),
                "Product name was not present in success message"
        );

        logger.info(
                "Add Product To Cart Test Passed"
        );
    }
}