package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;

    public Logger logger;

    public Properties p;


    // ============================================================
    // TEST SETUP
    // ============================================================

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "os"})
    public void setUp(
            @Optional("chrome") String browser,
            @Optional("windows") String os)
            throws IOException {

        logger = LogManager.getLogger(this.getClass());

        // --------------------------------------------------------
        // Load config.properties
        // --------------------------------------------------------

        FileReader file =
                new FileReader(
                        "./src/test/resources/config.properties"
                );

        p = new Properties();

        p.load(file);

        file.close();


        // --------------------------------------------------------
        // Read execution environment
        // --------------------------------------------------------

        String executionEnv =
                p.getProperty(
                        "executionEnv",
                        "local"
                );

        logger.info(
                "Execution environment: "
                        + executionEnv
        );

        logger.info(
                "Browser: "
                        + browser
        );

        logger.info(
                "Operating System: "
                        + os
        );


        // --------------------------------------------------------
        // Initialize WebDriver
        // --------------------------------------------------------

        if (executionEnv.equalsIgnoreCase("remote")) {

            driver =
                    initializeRemoteDriver(
                            browser,
                            os
                    );

        } else if (executionEnv.equalsIgnoreCase("local")) {

            driver =
                    initializeLocalDriver(
                            browser
                    );

        } else {

            throw new IllegalArgumentException(
                    "Invalid execution environment: "
                            + executionEnv
            );
        }


        // --------------------------------------------------------
        // Browser configuration
        // --------------------------------------------------------

        driver.manage().deleteAllCookies();


        /*
         * GitHub Actions automatically creates
         * an environment variable called CI.
         *
         * CI=true  -> GitHub Actions
         * CI=false -> Local machine
         */

        boolean isCI =
                "true".equalsIgnoreCase(
                        System.getenv("CI")
                );


        /*
         * Locally we maximize the browser.
         *
         * GitHub Actions uses the window size
         * configured in ChromeOptions.
         */

        if (!isCI) {

            driver
                    .manage()
                    .window()
                    .maximize();
        }


        // --------------------------------------------------------
        // Launch application
        // --------------------------------------------------------

        String appUrl =
                p.getProperty("appUrl");

        driver.get(appUrl);

        logger.info(
                "Application launched: "
                        + appUrl
        );


        // --------------------------------------------------------
        // CI diagnostics
        // --------------------------------------------------------

        logger.info(
                "Current URL: "
                        + driver.getCurrentUrl()
        );

        logger.info(
                "Page Title: "
                        + driver.getTitle()
        );

        logger.info(
                "Page Source Length: "
                        + driver.getPageSource().length()
        );


        // --------------------------------------------------------
        // Capture homepage screenshot in GitHub Actions
        // --------------------------------------------------------

        if (isCI) {

            captureCIHomepageScreenshot();
        }
    }


    // ============================================================
    // LOCAL DRIVER
    // ============================================================

    private WebDriver initializeLocalDriver(
            String browser) {

        boolean isCI =
                "true".equalsIgnoreCase(
                        System.getenv("CI")
                );

        logger.info(
                "Running in CI environment: "
                        + isCI
        );


        switch (browser.toLowerCase()) {


            // ----------------------------------------------------
            // Chrome
            // ----------------------------------------------------

            case "chrome":

                logger.info(
                        "Launching Chrome browser"
                );

                ChromeOptions chromeOptions =
                        new ChromeOptions();


                if (isCI) {

                    logger.info(
                            "Running Chrome in headless mode"
                    );

                    chromeOptions.addArguments(

                            "--headless=new",

                            "--no-sandbox",

                            "--disable-dev-shm-usage",

                            "--disable-gpu",

                            "--window-size=1920,1080"
                    );
                }


                return new ChromeDriver(
                        chromeOptions
                );


            // ----------------------------------------------------
            // Edge
            // ----------------------------------------------------

            case "edge":

                logger.info(
                        "Launching Edge browser"
                );

                EdgeOptions edgeOptions =
                        new EdgeOptions();


                if (isCI) {

                    logger.info(
                            "Running Edge in headless mode"
                    );

                    edgeOptions.addArguments(

                            "--headless=new",

                            "--no-sandbox",

                            "--disable-dev-shm-usage",

                            "--disable-gpu",

                            "--window-size=1920,1080"
                    );
                }


                return new EdgeDriver(
                        edgeOptions
                );


            // ----------------------------------------------------
            // Firefox
            // ----------------------------------------------------

            case "firefox":

                logger.info(
                        "Launching Firefox browser"
                );

                FirefoxOptions firefoxOptions =
                        new FirefoxOptions();


                if (isCI) {

                    logger.info(
                            "Running Firefox in headless mode"
                    );

                    firefoxOptions.addArguments(
                            "-headless"
                    );
                }


                return new FirefoxDriver(
                        firefoxOptions
                );


            // ----------------------------------------------------
            // Invalid browser
            // ----------------------------------------------------

            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: "
                                + browser
                );
        }
    }


    // ============================================================
    // REMOTE DRIVER / SELENIUM GRID
    // ============================================================

    @SuppressWarnings("deprecation")
    private WebDriver initializeRemoteDriver(
            String browser,
            String os)
            throws MalformedURLException {


        DesiredCapabilities capabilities =
                new DesiredCapabilities();


        // --------------------------------------------------------
        // Operating System
        // --------------------------------------------------------

        switch (os.toLowerCase()) {

            case "windows":

                capabilities.setPlatform(
                        Platform.WINDOWS
                );

                break;


            case "linux":

                capabilities.setPlatform(
                        Platform.LINUX
                );

                break;


            case "mac":

                capabilities.setPlatform(
                        Platform.MAC
                );

                break;


            default:

                throw new IllegalArgumentException(
                        "Unsupported operating system: "
                                + os
                );
        }


        // --------------------------------------------------------
        // Browser
        // --------------------------------------------------------

        switch (browser.toLowerCase()) {

            case "chrome":

                capabilities.setBrowserName(
                        "chrome"
                );

                break;


            case "firefox":

                capabilities.setBrowserName(
                        "firefox"
                );

                break;


            case "edge":

                capabilities.setBrowserName(
                        "MicrosoftEdge"
                );

                break;


            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: "
                                + browser
                );
        }


        // --------------------------------------------------------
        // Selenium Grid URL
        // --------------------------------------------------------

        String gridUrl =
                p.getProperty(
                        "gridUrl",
                        "http://localhost:4444/wd/hub"
                );


        logger.info(
                "Connecting to Selenium Grid: "
                        + gridUrl
        );


        return new RemoteWebDriver(
                new URL(gridUrl),
                capabilities
        );
    }


    // ============================================================
    // CI HOMEPAGE SCREENSHOT
    // ============================================================

    private void captureCIHomepageScreenshot() {

        try {

            File screenshotDirectory =
                    new File(
                            "target/ci-screenshots"
                    );


            if (!screenshotDirectory.exists()) {

                screenshotDirectory.mkdirs();
            }


            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );


            File destination =
                    new File(
                            screenshotDirectory,
                            "homepage.png"
                    );


            FileUtils.copyFile(
                    source,
                    destination
            );


            logger.info(
                    "CI homepage screenshot saved: "
                            + destination.getAbsolutePath()
            );


        } catch (Exception e) {

            logger.error(
                    "Unable to capture CI homepage screenshot",
                    e
            );
        }
    }


    // ============================================================
    // TEARDOWN
    // ============================================================

    @AfterClass(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {

            logger.info(
                    "Closing browser"
            );

            driver.quit();

            driver = null;
        }
    }


    // ============================================================
    // RANDOM STRING
    // ============================================================

    public String randomString() {

        return RandomStringUtils
                .randomAlphabetic(5);
    }


    // ============================================================
    // RANDOM PASSWORD
    // ============================================================

    public String randomAlphaNumeric() {

        return RandomStringUtils
                .randomAlphanumeric(8)
                + "@1";
    }


    // ============================================================
    // RANDOM NUMBER
    // ============================================================

    public String randomNumber() {

        return RandomStringUtils
                .randomNumeric(10);
    }


    // ============================================================
    // SCREENSHOT ON TEST FAILURE
    // ============================================================

    public static String screenCapture(
            String testName)
            throws IOException {


        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern(
                                "yyyyMMdd_HHmmss"
                        );


        String timeStamp =
                LocalDateTime
                        .now()
                        .format(
                                formatter
                        );


        TakesScreenshot screenshot =
                (TakesScreenshot) driver;


        File source =
                screenshot
                        .getScreenshotAs(
                                OutputType.FILE
                        );


        File screenshotDirectory =
                new File(
                        "./screenshots"
                );


        if (!screenshotDirectory.exists()) {

            screenshotDirectory.mkdirs();
        }


        File destination =
                new File(
                        screenshotDirectory,

                        testName
                                + "_"
                                + timeStamp
                                + ".png"
                );


        FileUtils.copyFile(
                source,
                destination
        );


        return destination
                .getAbsolutePath();
    }
}