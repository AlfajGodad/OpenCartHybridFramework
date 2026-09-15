package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import pageObjects.LogoutPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utility.DataProvider1;

public class TC003_DataProviderLogin extends BaseClass {

    @Test(
        dataProvider = "LoginData",
        dataProviderClass = DataProvider1.class
    )
    public void verifyLoginDDT(
            String email,
            String password,
            String expectedResult) {

        logger.info("--------------------------------------------");
        logger.info("Starting Data Driven Login Test");
        logger.info("Email: " + email);
        logger.info("Expected Result: " + expectedResult);

        expectedResult = expectedResult.trim();

        /*
         * Start every DataProvider iteration
         * from the Login page.
         */
        driver.get(
                p.getProperty("appUrl")
                + "index.php?route=account/login"
        );

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        /*
         * VALID LOGIN
         */
        if (expectedResult.equalsIgnoreCase("Valid")) {

            MyAccountPage myAccountPage =
                    new MyAccountPage(driver);

            boolean loginSuccessful =
                    myAccountPage.isMyAccountDisplayed();

            Assert.assertTrue(
                    loginSuccessful,
                    "Expected login to succeed, but it failed"
            );

            logger.info("Valid login test passed");

            /*
             * Logout so that the next DataProvider
             * iteration starts without an active session.
             */
            myAccountPage.clickMyAccount();
            myAccountPage.clickLogout();
            
            LogoutPage logoutPage= new LogoutPage(driver);
            logoutPage.clickContinue();
           

            logger.info("User logged out successfully");
        }

        /*
         * INVALID LOGIN
         */
        else if (expectedResult.equalsIgnoreCase("Invalid")) {

            String warningMessage =
                    loginPage.getWarningMessage();

            logger.info(
                    "Warning Message: "
                    + warningMessage
            );

            boolean invalidCredentialsWarning =
                    warningMessage.contains(
                        "No match for E-Mail Address and/or Password"
                    );

            boolean loginAttemptsExceededWarning =
                    warningMessage.contains(
                        "exceeded allowed number of login attempts"
                    );

            Assert.assertTrue(
                    invalidCredentialsWarning
                    || loginAttemptsExceededWarning,
                    "Expected invalid login warning was not displayed. "
                    + "Actual Message: "
                    + warningMessage
            );

            logger.info(
                    "Invalid login was rejected successfully"
            );
        }

        /*
         * WRONG VALUE IN EXCEL
         */
        else {

            Assert.fail(
                    "Invalid ExpectedResult value in Excel: "
                    + expectedResult
                    + ". Use Valid or Invalid."
            );
        }

        logger.info("Data Driven Login Test Completed");
    }
}