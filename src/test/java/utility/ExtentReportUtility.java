package utility;


import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportUtility implements ITestListener{
	ExtentSparkReporter sparkReporter;
	ExtentReports extent;
	ExtentTest test;
	public String repName;
	
	 public void onStart(ITestContext context) {
		 DateTimeFormatter formatter =
	                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	        String timeStamp = LocalDateTime.now().format(formatter);
	       String repName= "TestReport"+ timeStamp +".html";
	        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
	        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
	        sparkReporter.config().setReportName("opencart functional testing");
	        sparkReporter.config().setTheme(Theme.DARK);
	        extent= new ExtentReports();
	        extent.attachReporter(sparkReporter);
	        extent.setSystemInfo("Application name", "Opencart");
	        String os=context.getCurrentXmlTest().getParameter("os");
	        extent.setSystemInfo("Browser", os);
	        extent.setSystemInfo("OS", "Windows");
	        
	        String br=context.getCurrentXmlTest().getParameter("browser");
	        extent.setSystemInfo("Browser", br);
	        extent.setSystemInfo("User", System.getProperty("user.name"));
		  }
	 
	 public void onTestSuccess(ITestResult result) {
		    test= extent.createTest(result.getClass().getName());
		    test.assignCategory(result.getMethod().getGroups());
		    test.log(Status.PASS, result.getName()+" Test is passed");
		  }
	 
	 public void onTestFailure(ITestResult result) {
		 test= extent.createTest(result.getClass().getName());
		    test.assignCategory(result.getMethod().getGroups());
		    test.log(Status.FAIL, result.getName()+" Test is failed");
		    test.log(Status.INFO, result.getThrowable().getMessage());
		    try {
		    	String path= BaseClass.screenCapture(result.getName());
		    	test.addScreenCaptureFromPath(path);
		    } catch(Exception e) {
		    	e.printStackTrace();
		    }
		  }
	 
	 public void onTestSkipped(ITestResult result) {
		 test= extent.createTest(result.getClass().getName());
		    test.assignCategory(result.getMethod().getGroups());
		    test.log(Status.SKIP, result.getName()+" Test is Skipped");
		    test.log(Status.INFO, result.getThrowable().getMessage());
		  }
	 
	 public void onFinish(ITestContext context) {
		    extent.flush();
		    String pathOfExtent= ".\\reports\\"+ repName;
	    File file= new File(pathOfExtent);
	    if(file.exists()) {
	    	try {
	            new ProcessBuilder(
	                    "cmd",
	                    "/c",
	                    "start",
	                    "",
	                    file.getAbsolutePath()
	            ).start();

	            System.out.println("Extent Report opened successfully.");
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    }
		    
		  }
	 
	 
	
	

}
