package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginPage extends BaseClass {

    @Test
    public void verifyLogin() {

        logger.info("Starting login test");

        HomePage homePage =
                new HomePage(driver);

        homePage.clickMyAccount();
        homePage.clickLogin();

        LoginPage loginPage =
                new LoginPage(driver);

        String email =
                System.getenv("OPENCART_USERNAME");

        String password =
                System.getenv("OPENCART_PASSWORD");

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
                "Login credentials are not configured. "
                + "Use OPENCART_USERNAME / OPENCART_PASSWORD "
                + "or Maven -D properties."
            );
        }

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        MyAccountPage myAccountPage =
                new MyAccountPage(driver);

        Assert.assertTrue(
                myAccountPage.isMyAccountDisplayed(),
                "Login failed - My Account page was not displayed"
        );

        logger.info("Login test passed");
    }
}