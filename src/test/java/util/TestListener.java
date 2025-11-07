package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.*;

import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {  // ISuiteListener gerekmez
    private static final ExtentReports extent = ExtentManager.getReporter();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override public void onTestSuccess(ITestResult result) {
        test.get().pass("Passed");
    }

    @Override public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        BaseTest testInstance = (BaseTest) result.getInstance();
        WebDriver driver = testInstance.getDriver();
        if (driver != null) {
            String path = takeScreenshot(driver, result.getMethod().getMethodName());

                test.get().fail("Screenshot",
                        MediaEntityBuilder.createScreenCaptureFromPath(path).build());

        }
    }

    @Override public void onFinish(ITestContext context) {

        extent.flush();
    }

    private String takeScreenshot(WebDriver driver, String name) {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dir  = Paths.get(System.getProperty("user.dir"), "target", "screenshots").toString();
        new File(dir).mkdirs();
        String filePath = Paths.get(dir, name + "_" + time + ".png").toString();
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try { FileUtils.copyFile(src, new File(filePath)); } catch (IOException ignored) {}
        return filePath;
    }
}
