package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	@BeforeClass(groups= {"Sanity", "Master", "DataDriven"})
	@Parameters({"browser","os"})
	public void setUp(String br, String os) throws IOException {
		logger= LogManager.getLogger(this.getClass());
		FileReader file= new FileReader("./src//test//resources//confi.properties");
		p= new Properties();
		p.load(file);
		String hubUrl="http://192.168.31.192:4444/wd/hub";
		if(p.getProperty("excecution_Env").equalsIgnoreCase("remote")) {
			DesiredCapabilities cap= new DesiredCapabilities();
			if(os.equals("windows") ) {
			cap.setPlatform(Platform.WIN11);
			
			}
			else if(os.equals("linux")  ) {
				cap.setPlatform(Platform.LINUX);
				
				}
			else if(os.equals("mac")  ) {
				cap.setPlatform(Platform.MAC);
				
				}
			
			switch (br.toLowerCase()){
			case "chrome": cap.setBrowserName("chrome");
			 driver= new RemoteWebDriver(new URL(hubUrl), cap); break;
			case "firefox": cap.setBrowserName("firefox");
			 driver= new RemoteWebDriver(new URL(hubUrl), cap);break;
			case "edge": cap.setBrowserName("MicrosoftEdge");
			 driver= new RemoteWebDriver(new URL(hubUrl), cap);break;
			 default: System.out.println("Invalid browser"); return;
			}
			
			
		}
		
		
		if(p.getProperty("excecution_Env").equalsIgnoreCase("local")) {
		
		switch(br.toLowerCase()) {
		case "chrome": WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver(); break;
		case "edge":
			System.setProperty("webdriver.edge.driver", "C:\\Study\\API Testing\\msedgedriver.exe");
			 driver = new EdgeDriver(); break;
		default : System.out.println("Invalid browser");	return; 
		}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(p.getProperty("appUrl"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity", "Master", "DataDriven"})
	public void tearDown() {
		driver.quit();
		
	}
	
	public String randomString() {
		String random= RandomStringUtils.randomAlphabetic(4);
		return random;
	}
	
	public String randomAlphaNumeric() {
		return RandomStringUtils.randomAlphanumeric(5)+ "#";
	}
	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}
	
	public static String screenCapture(String name) throws IOException {
		DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        String timeStamp = LocalDateTime.now().format(formatter);
		
		TakesScreenshot sc= (TakesScreenshot) driver;
		File scr=sc.getScreenshotAs(OutputType.FILE);
			File file= new File(".\\ScreenShots\\FailedTest " +name + timeStamp +".png");
			
			FileUtils.copyFile(scr, file);
			String path= file.getAbsolutePath();
			return path;
			
		
	}

}
