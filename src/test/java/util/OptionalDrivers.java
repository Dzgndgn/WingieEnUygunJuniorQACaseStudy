package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionalDrivers {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static void Driver(String browser){
        if(browser.toLowerCase().equals("firefox")){
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            driver.set(new FirefoxDriver(firefoxOptions));
        }
        else{
            ChromeOptions chromeOptions = new ChromeOptions();
            driver.set(new ChromeDriver(chromeOptions));
        }

    }
    public static WebDriver getDriver(){
        return driver.get();
    }
    public static void quitDriver(){
        if(driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
