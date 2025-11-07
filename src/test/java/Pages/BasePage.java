package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {
    protected WebDriver driver;
    public BasePage(WebDriver driver){
        this.driver = driver;
    }
    protected void fillText(By locator,String text){
        driver.findElement(locator).sendKeys(text);
        try {
            Thread.sleep(200);
        }catch  (Exception ignored) {}
    }
    protected void scrollInto(By locator){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement webElement = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", webElement);
    }
}
