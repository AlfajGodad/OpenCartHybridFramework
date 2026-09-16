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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseClass {

    public static WebDriver driver;

    public Logger logger;
    public Properties p;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "os"})
    public void setUp(
            @Optional("chrome") String browser,
            @Optional("windows") String os)
            throws IOException {

        logger = LogManager.getLogger(this.getClass());

        // Load configuration
        FileReader file =
                new FileReader("./src/test/resources/config.properties");

        p = new Properties();
        p.load(file);
        file.close();

        String executionEnv =
                p.getProperty("executionEnv", "local");

        logger.info("Execution environment: " + executionEnv);
        logger.info("Browser: " + browser);
        logger.info("Operating System: " + os);

        if (executionEnv.equalsIgnoreCase("remote")) {

            driver = initializeRemoteDriver(browser, os);

        } else if (executionEnv.equalsIgnoreCase("local")) {

            driver = initializeLocalDriver(browser);

        } else {

            throw new IllegalArgumentException(
                    "Invalid execution environment: "
                            + executionEnv);
        }

        driver.manage().deleteAllCookies();

        boolean isCI =
                "true".equalsIgnoreCase(
                        System.getenv("CI")
                );

        if (!isCI) {

            driver.manage()
                    .window()
                    .maximize();
        }

        driver.get(
                p.getProperty("appUrl")
        );

        logger.info(
                "Application launched: "
                        + p.getProperty("appUrl"));
    }

    private WebDriver initializeLocalDriver(String browser) {

        /*
         * GitHub Actions automatically provides
         * the CI environment variable.
         */
        boolean isCI =
                "true".equalsIgnoreCase(
                        System.getenv("CI")
                );

        logger.info("Running in CI environment: " + isCI);

        switch (browser.toLowerCase()) {

            case "chrome":

                logger.info("Launching Chrome browser");

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
                            "--window-size=1920,1080"
                    );
                }

                return new ChromeDriver(
                        chromeOptions
                );


            case "edge":

                logger.info("Launching Edge browser");

                EdgeOptions edgeOptions =
                        new EdgeOptions();

                if (isCI) {

                    edgeOptions.addArguments(
                            "--headless=new",
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--window-size=1920,1080"
                    );
                }

                return new EdgeDriver(
                        edgeOptions
                );


            case "firefox":

                logger.info("Launching Firefox browser");

                FirefoxOptions firefoxOptions =
                        new FirefoxOptions();

                if (isCI) {

                    firefoxOptions.addArguments(
                            "-headless"
                    );
                }

                return new FirefoxDriver(
                        firefoxOptions
                );


            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: "
                                + browser
                );
        }
    }

    @SuppressWarnings("deprecation")
	private WebDriver initializeRemoteDriver(
            String browser,
            String os)
            throws MalformedURLException {

        DesiredCapabilities capabilities =
                new DesiredCapabilities();

        /*
         * Operating System
         */
        switch (os.toLowerCase()) {

            case "windows":
                capabilities.setPlatform(
                        Platform.WINDOWS);
                break;

            case "linux":
                capabilities.setPlatform(
                        Platform.LINUX);
                break;

            case "mac":
                capabilities.setPlatform(
                        Platform.MAC);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported operating system: "
                                + os);
        }

        /*
         * Browser
         */
        switch (browser.toLowerCase()) {

            case "chrome":
                capabilities.setBrowserName("chrome");
                break;

            case "firefox":
                capabilities.setBrowserName("firefox");
                break;

            case "edge":
                capabilities.setBrowserName(
                        "MicrosoftEdge");
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: "
                                + browser);
        }

        String gridUrl =
                p.getProperty(
                        "gridUrl",
                        "http://localhost:4444/wd/hub");

        logger.info(
                "Connecting to Selenium Grid: "
                        + gridUrl);

        return new RemoteWebDriver(
                new URL(gridUrl),
                capabilities);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {

            logger.info("Closing browser");

            driver.quit();

            driver = null;
        }
    }

    /*
     * Generates random alphabetic text.
     */
    public String randomString() {

        return RandomStringUtils
                .randomAlphabetic(5);
    }

    /*
     * Generates random alphanumeric password.
     */
    public String randomAlphaNumeric() {

        return RandomStringUtils
                .randomAlphanumeric(8)
                + "@1";
    }

    /*
     * Generates random 10-digit number.
     */
    public String randomNumber() {

        return RandomStringUtils
                .randomNumeric(10);
    }

    /*
     * Capture screenshot when a test fails.
     */
    public static String screenCapture(
            String testName)
            throws IOException {

        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern(
                                "yyyyMMdd_HHmmss");

        String timeStamp =
                LocalDateTime
                        .now()
                        .format(formatter);

        TakesScreenshot screenshot =
                (TakesScreenshot) driver;

        File source =
                screenshot.getScreenshotAs(
                        OutputType.FILE);

        File screenshotDirectory =
                new File("./screenshots");

        if (!screenshotDirectory.exists()) {
            screenshotDirectory.mkdirs();
        }

        File destination =
                new File(
                        screenshotDirectory,
                        testName
                                + "_"
                                + timeStamp
                                + ".png");

        FileUtils.copyFile(
                source,
                destination);

        return destination.getAbsolutePath();
    }
}