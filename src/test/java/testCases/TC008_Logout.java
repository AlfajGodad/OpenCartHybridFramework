package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.LogoutPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC008_Logout extends BaseClass {

    @Test
    public void verifyLogout() {

        logger.info(
                "Starting Logout Test"
        );

        /*
         * STEP 1
         * Get valid credentials
         */

        String email =
                System.getenv(
                        "OPENCART_USERNAME"
                );

        String password =
                System.getenv(
                        "OPENCART_PASSWORD"
                );

        /*
         * Maven fallback
         */
        if (email == null || email.isBlank()) {

            email =
                    System.getProperty(
                            "opencart.username"
                    );
        }

        if (password == null || password.isBlank()) {

            password =
                    System.getProperty(
                            "opencart.password"
                    );
        }

        if (email == null || password == null) {

            Assert.fail(
                    "Login credentials are not configured."
            );
        }


        /*
         * STEP 2
         * Open Login page
         */

        HomePage homePage =
                new HomePage(driver);

        homePage.clickMyAccount();

        homePage.clickLogin();


        /*
         * STEP 3
         * Login
         */

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.enterEmail(
                email
        );

        loginPage.enterPassword(
                password
        );

        loginPage.clickLogin();


        /*
         * STEP 4
         * Verify Login
         */

        MyAccountPage myAccountPage =
                new MyAccountPage(driver);

        Assert.assertTrue(
                myAccountPage.isMyAccountDisplayed(),
                "Login failed before logout test"
        );

        logger.info(
                "Login successful"
        );


        /*
         * STEP 5
         * Logout
         */
        LogoutPage logoutPage =
                new LogoutPage(driver);

        myAccountPage.clickMyAccount();
        myAccountPage.clickLogout();
        logoutPage.clickContinue();

        logger.info(
                "Clicked Logout"
        );


        /*
         * STEP 6
         * Verify user is logged out
         */

        HomePage homePageAfterLogout =
                new HomePage(driver);

        /*
         * Open My Account dropdown
         */
        homePageAfterLogout.clickMyAccount();

        /*
         * After successful logout,
         * Login option should be available again.
         */
        boolean loginOptionDisplayed =
                homePageAfterLogout.isLoginDisplayed();

        Assert.assertTrue(
                loginOptionDisplayed,
                "Logout failed - Login option was not displayed"
        );

        logger.info(
                "Login option displayed after logout"
        );

        logger.info(
                "Logout Test Passed"
        );
    }
}