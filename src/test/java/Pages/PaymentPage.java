package Pages;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentPage extends BasePage {
    public PaymentPage(WebDriver driver){
        super(driver);
    }
    final By CardNumber = By.xpath("//input[@data-testid='cardNumber']");
    final By CVV = By.xpath("//label[@data-testid='CVV-input-comp']");
    final By CardMonth = By.xpath("//input[@data-testid='cardMonth-input']");
    final By CardMonthOption=By.xpath("//button[@data-testid='cardMonth-option-0']");
    final By CardYearOption=By.xpath("//button[@data-testid='cardYear-option-1']");

    final By CardYear = By.xpath("//input[@data-testid='cardYear-input']");
    final By PaymentBtn = By.xpath("//button[@data-testid='payment-form-submit-button']");

    final By priceInt = By.xpath("//b[@data-testid='total-price']//span[@class ='money-int']");
    final By priceDecimal =By.xpath("//b[@data-testid='total-price']//span[@class ='money-decimal']");
    public boolean isLoaded(){
        return driver.getCurrentUrl().contains("odeme");
    }
    public int getTotalPrice(){
        String PriceInt =driver.findElement(priceInt).getText();
        String PriceDecimal = driver.findElement(priceDecimal).getText();
        String NewStr = PriceInt.replace(".","");
        double doubleVal = Integer.parseInt(NewStr);
        return (int) doubleVal;
    }
    public PaymentPage fillCardNum(String number){
        fillText(CardNumber,number);
        return this;
    }
    public PaymentPage fillCardMonth(){
        driver.findElement(CardMonth).click();
        driver.findElement(CardMonthOption).click();
        return this;
    }
    public PaymentPage fillCardYear(){
        driver.findElement(CardYear).click();
        driver.findElement(CardYearOption).click();
        return this;
    }
    public  PaymentPage fillCVV(String cvv){
        fillText(CVV,cvv);
        return this;
    }
    public void Payment(){
        driver.findElement(PaymentBtn).click();
    }
}
