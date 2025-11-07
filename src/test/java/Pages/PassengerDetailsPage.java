package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Locale;

public class PassengerDetailsPage extends BasePage {
    //private final WebDriver driver;
    private final WebDriverWait wait;
    public PassengerDetailsPage(WebDriver driver){
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    final By priceInfo = By.xpath("//div[@id ='priceInfoGrandTotal']");
    final By EmailInput = By.xpath("//input[@id ='contact_email']");
    final By phoneNumberInput =By.xpath("//input[@id='contact_cellphone']");
    final By nameInput = By.xpath("//input[@id='firstName_0']");
    final By lastName = By.xpath("//input[@id='lastName_0']");
    final By IdentityNumber = By.xpath("//input[@data-testid ='reservation-publicid-TR-input']");
    final By maleCheckbox = By.xpath("//label[@for='gender_M_0']");
    final By femaleCheckbox = By.xpath("//label[@for='gender_F_0']");
    final By GoToPaymentBtn = By.xpath("//button[@id='continue-button']");
    final By AirlineName = By.xpath("//div[@class ='card card-flight-info']//span[@data-testid ='departureAirlineName']");
    final By departureTime = By.xpath("//div[@class ='card card-flight-info']//span[@data-testid ='departureFlightTime']");
    final By BirthdayDay = By.xpath("//select[@id='birthDateDay_0']");
    final By BirtMonth = By.xpath("//select[@id ='birthDateMonth_0']");
    final By BirthYear = By.xpath("//select[@id = 'birthDateYear_0']");
    public boolean isLoaded(){
       return driver.getCurrentUrl().contains("rezervasyon");
    }
    public String getAirlineName(){
        return driver.findElement(AirlineName).getText();
    }
    public String getDepartureTime(){
        return driver.findElement(departureTime).getText();
    }
    public PassengerDetailsPage fillEmail(String mail){
        fillText(EmailInput,mail);
        return this;
    }
    public PassengerDetailsPage fillNumber(String phoneNumber){
        fillText(phoneNumberInput,phoneNumber);
        return this;
    }
    public PassengerDetailsPage fillName(String name){
        fillText(nameInput,name);
        return this;
    }
    public PassengerDetailsPage fillLastName(String lastname){
        fillText(lastName,lastname);
        return this;
    }
    public PassengerDetailsPage fillIdentityNumber(String TC){
        fillText(IdentityNumber,TC);
        return this;
    }
    public PassengerDetailsPage gender(String gender){
        if(gender == null || gender.trim().isEmpty())
            driver.findElement(maleCheckbox).click();
        if(gender.toLowerCase().equals( "kadın") || gender.toLowerCase().equals("female"))
            driver.findElement(femaleCheckbox).click();
        else
            driver.findElement(maleCheckbox).click();
        return this;
    }
    public PaymentPage GoToPayment(){
        scrollInto(GoToPaymentBtn);
        driver.findElement(GoToPaymentBtn).click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new PaymentPage(driver);
    }
    public PassengerDetailsPage fillBirthday(String day,String month,String year){
        WebElement Dayelement =driver.findElement(BirthdayDay);
        Select dayselection = new Select(Dayelement);
        dayselection.selectByValue(day);


        WebElement element = driver.findElement(BirtMonth);
        Select monthSelection = new Select(element);
        monthSelection.selectByValue(month);
        driver.findElement(BirthYear).click();
        driver.findElement(BirthYear).sendKeys(year);
        //driver.findElement(BirthYear).click();
        return this;
    }
    public int getPriceInfo(){
        WebElement price = driver.findElement(priceInfo);
        String Price = price.getAttribute("data-price");
        double doubleval = Double.parseDouble(Price);
        return (int) doubleval;
    }
}
