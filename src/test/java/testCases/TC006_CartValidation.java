package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import pageObjects.ShoppingCartPage;
import testBase.BaseClass;

public class TC006_CartValidation extends BaseClass {

    @Test
    public void verifyProductInShoppingCart() {

        logger.info(
                "Starting Shopping Cart Validation Test"
        );

        String productName = "MacBook";

        /*
         * STEP 1
         * Search product
         */
        HomePage homePage =
                new HomePage(driver);

        logger.info(
                "Searching for product: "
                + productName
        );

        homePage.enterSearchProduct(
                productName
        );

        homePage.clickSearch();


        /*
         * STEP 2
         * Verify product exists
         */
        SearchPage searchPage =
                new SearchPage(driver);

        Assert.assertTrue(
                searchPage.isProductDisplayed(
                        productName
                ),
                "Product not found in search results: "
                + productName
        );

        logger.info(
                "Product found successfully"
        );


        /*
         * STEP 3
         * Add product to cart
         */
        searchPage.addProductToCart(
                productName
        );

        logger.info(
                "Product added to cart"
        );


        /*
         * STEP 4
         * Verify success message
         */
        String successMessage =
                searchPage.getSuccessMessage();

        Assert.assertTrue(
                successMessage.contains(
                        "Success: You have added"
                ),
                "Add to cart success message "
                + "was not displayed"
        );

        logger.info(
                "Add to cart success message verified"
        );


        /*
         * STEP 5
         * Open Shopping Cart
         */
        searchPage.openShoppingCart();

        logger.info(
                "Shopping Cart opened"
        );


        /*
         * STEP 6
         * Validate Shopping Cart
         */
        ShoppingCartPage cartPage =
                new ShoppingCartPage(driver);

        String heading =
                cartPage.getShoppingCartHeading();

        logger.info(
                "Cart Heading: "
                + heading
        );

        Assert.assertTrue(
                heading.contains(
                        "Shopping Cart"
                ),
                "Shopping Cart page "
                + "was not displayed"
        );


        /*
         * STEP 7
         * Verify product
         */
        boolean productPresent =
                cartPage.isProductPresent(
                        productName
                );

        Assert.assertTrue(
                productPresent,
                "Expected product was not found "
                + "in Shopping Cart: "
                + productName
        );

        logger.info(
                "Product verified successfully "
                + "in Shopping Cart: "
                + productName
        );

        logger.info(
                "Shopping Cart Validation Test Passed"
        );
    }
}
