package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC007_InvalidLogin extends BaseClass {

    @Test
    public void verifyInvalidLogin() {

        logger.info(
                "Starting Invalid Login Test"
        );

        /*
         * STEP 1
         * Open Login page
         */
        HomePage homePage =
                new HomePage(driver);

        homePage.clickMyAccount();
        homePage.clickLogin();

        logger.info(
                "Login page opened"
        );


        /*
         * STEP 2
         * Enter invalid credentials
         */
        LoginPage loginPage =
                new LoginPage(driver);

        String invalidEmail =
                "invaliduser@test.com";

        String invalidPassword =
                "WrongPassword123";

        loginPage.enterEmail(
                invalidEmail
        );

        loginPage.enterPassword(
                invalidPassword
        );

        logger.info(
                "Invalid credentials entered"
        );


        /*
         * STEP 3
         * Click Login
         */
        loginPage.clickLogin();


        /*
         * STEP 4
         * Get warning message
         */
        String warningMessage =
                loginPage.getWarningMessage();

        logger.info(
                "Warning Message: "
                + warningMessage
        );


        /*
         * STEP 5
         * Validate possible warning messages
         */
        boolean invalidCredentialsWarning =
                warningMessage.contains(
                        "No match for E-Mail Address and/or Password"
                );

        boolean attemptsExceededWarning =
                warningMessage.contains(
                        "exceeded allowed number of login attempts"
                );


        Assert.assertTrue(
                invalidCredentialsWarning
                || attemptsExceededWarning,
                "Expected invalid login warning "
                + "was not displayed. Actual Message: "
                + warningMessage
        );

        logger.info(
                "Invalid Login Test Passed"
        );
    }
}