package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import util.OptionalDrivers;

import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    protected final String ENUYGUN_URL = "https://www.enuygun.com/";

    @Parameters("browser")
    @BeforeMethod
    public void firstOpen(@Optional("chrome") String browser) {


        OptionalDrivers.Driver(browser);

        driver = OptionalDrivers.getDriver();
        driver.manage().window().maximize();
        driver.get(ENUYGUN_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@data-testid, 'search-round-trip')]")
        ));
    }

    public WebDriver getDriver(){
        return OptionalDrivers.getDriver();
    }
    @AfterMethod(alwaysRun = true)
    public void close() {
        OptionalDrivers.quitDriver();
    }
}
