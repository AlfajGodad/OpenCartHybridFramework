package utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import testBase.BaseClass;

public class ExtentReportUtility
        implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;

    private String reportName;

    @Override
    public void onStart(ITestContext context) {

        String timeStamp =
                new SimpleDateFormat(
                        "yyyy.MM.dd.HH.mm.ss"
                ).format(new Date());

        reportName =
                "TestReport-"
                        + timeStamp
                        + ".html";

        sparkReporter =
                new ExtentSparkReporter(
                        "./reports/"
                                + reportName
                );

        sparkReporter.config()
                .setDocumentTitle(
                        "OpenCart Automation Report"
                );

        sparkReporter.config()
                .setReportName(
                        "Functional Automation Testing"
                );

        extent =
                new ExtentReports();

        extent.attachReporter(
                sparkReporter
        );

        String browser =
                context
                        .getCurrentXmlTest()
                        .getParameter("browser");

        String os =
                context
                        .getCurrentXmlTest()
                        .getParameter("os");

        extent.setSystemInfo(
                "Application",
                "OpenCart"
        );

        extent.setSystemInfo(
                "Browser",
                browser
        );

        extent.setSystemInfo(
                "OS",
                os
        );

        extent.setSystemInfo(
                "Tester",
                System.getProperty("user.name")
        );
    }

    @Override
    public void onTestSuccess(
            ITestResult result) {

        test =
                extent.createTest(
                        result.getName()
                );

        test.log(
                Status.PASS,
                "Test passed"
        );
    }

    @Override
    public void onTestFailure(
            ITestResult result) {

        test =
                extent.createTest(
                        result.getName()
                );

        test.log(
                Status.FAIL,
                "Test failed"
        );

        test.log(
                Status.FAIL,
                result.getThrowable()
        );

        try {

            String screenshotPath =
                    BaseClass.screenCapture(
                            result.getName()
                    );

            test.addScreenCaptureFromPath(
                    screenshotPath
            );

        } catch (IOException e) {

            test.log(
                    Status.WARNING,
                    "Screenshot could not be captured: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public void onTestSkipped(
            ITestResult result) {

        test =
                extent.createTest(
                        result.getName()
                );

        test.log(
                Status.SKIP,
                "Test skipped"
        );
    }

    @Override
    public void onFinish(
            ITestContext context) {

        if (extent != null) {
            extent.flush();
        }

        File report =
                new File(
                        "./reports/"
                                + reportName
                );

        System.out.println(
                "Extent Report generated at: "
                        + report.getAbsolutePath()
        );
    }
}