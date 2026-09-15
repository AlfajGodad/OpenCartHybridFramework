package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class TC001_RegistrationPage extends BaseClass {

    @Test
    public void verifyAccountRegistration() {

        logger.info("Starting registration test");

        HomePage homePage = new HomePage(driver);

        homePage.clickMyAccount();
        homePage.clickRegister();

        RegistrationPage registrationPage =
                new RegistrationPage(driver);

        String firstName = randomString();
        String lastName = randomString();

        String email =
                randomString().toLowerCase()
                        + "@gmail.com";

        String telephone =
                randomNumber();

        String password =
                randomAlphaNumeric();

        logger.info(
                "Entering registration details"
        );

        registrationPage.setFirstName(firstName);
        registrationPage.setLastName(lastName);
        registrationPage.setEmail(email);
        registrationPage.setTelephone(telephone);
        registrationPage.setPassword(password);
        registrationPage.setConfirmPassword(password);

        registrationPage.acceptPrivacyPolicy();
        registrationPage.clickContinue();

        /*
         * Validate successful account creation
         */

        String expectedMessage =
                "Your Account Has Been Created!";

        String actualMessage =
                driver.getTitle();

        Assert.assertEquals(
                actualMessage,
                expectedMessage,
                "Account registration failed"
        );

        logger.info(
                "Account registration completed successfully"
        );
    }
}