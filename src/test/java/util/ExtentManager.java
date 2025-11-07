package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {
    private static ExtentReports extent;
    public static final String REPORT_DIR = "target/extent";
    public static final String SCREENSHOT_DIR = "target/screenshots";

    public static synchronized ExtentReports getReporter() {
        if (extent == null) {
            // Klasörleri garanti et
            new File(REPORT_DIR).mkdirs();
            new File(SCREENSHOT_DIR).mkdirs();

            String reportPath = REPORT_DIR + "/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("UI Test Report");
            spark.config().setDocumentTitle("Extent Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Env", "QA");
        }
        return extent;
    }
}
