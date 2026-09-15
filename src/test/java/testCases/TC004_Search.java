package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC004_Search extends BaseClass {

    @Test
    public void verifyProductSearch() {

        logger.info(
                "Starting Product Search Test"
        );

        String productName = "MacBook";

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

        SearchPage searchPage =
                new SearchPage(driver);

        boolean productFound =
                searchPage.isProductDisplayed(
                        productName
                );

        Assert.assertTrue(
                productFound,
                "Product was not found in search results: "
                + productName
        );

        logger.info(
                "Product found successfully: "
                + productName
        );

        logger.info(
                "Product Search Test Passed"
        );
    }
}