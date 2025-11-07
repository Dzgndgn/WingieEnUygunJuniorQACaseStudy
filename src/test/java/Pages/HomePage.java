package Pages;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.util.ClassUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.swing.text.Document;
import java.time.Duration;

public class HomePage  {
    private final WebDriver driver;
    public HomePage(WebDriver driver){
        this.driver = driver;
    }
     final By ListingHotelCheck= By.xpath("(//div//label[@data-testid='flight-oneWayCheckbox-label'])[2]");
     final By gidisdönüs= (By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div/div[2]/div/div[2]/div/div/div/form/div/div[1]/div/div/div/div[1]/div[2]"));
     final By Nereden = (By.xpath("//input[contains(@data-testid, 'endesign-flight-origin-autosuggestion-input')]"));
     final By Nereye  = (By.xpath("//input[contains(@data-testid, 'endesign-flight-destination-autosuggestion-input')]"));
     final By GidisTarihi = (By.xpath("//input[contains(@data-testid, 'enuygun-homepage-flight-departureDate-datepicker-input')]"));
    final By DönüsTarihi = (By.xpath("//input[contains(@data-testid, 'enuygun-homepage-flight-returnDate-datepicker-input')]"));
    final By BiletButton = (By.xpath("//button[contains(@data-testid, 'enuygun-homepage-flight-submitButton')]"));
    final By IstanbulButton = By.xpath("//button[contains(@data-testid, 'flight-search-popular-item-origin-0')]");
    final By AnkaraButton = By.xpath("//button[contains(@data-testid, 'flight-search-popular-item-destination-1')]");
    final By NicosiaBtn = By.xpath("//button[contains(@data-testid, 'flight-search-popular-item-destination-8')]");
    public HomePage gidisDönüs(){
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.elementToBeClickable(gidisdönüs)).click();
       driver.findElement(gidisdönüs).click();
        return this;
    }
    public HomePage fromCity(String city){
        driver.findElement(Nereden).click();
      driver.findElement(IstanbulButton).click();
        //  driver.findElement(Nereden).sendKeys(city);
        return this;
    }
    public HomePage fromToCity(String city){
        driver.findElement(Nereye).click();
       // driver.findElement(Nereye).sendKeys(city);
        driver.findElement(AnkaraButton).click();
        return this;
    }
    /*public HomePage Gidis(String year,String month,String day){
        driver.findElement(GidisTarihi).click();

        String dateValue = String.format("//*[@data-testid='day-%s-%s-%s']", year, month, day);
        driver.findElement(By.xpath(dateValue)).click();
        return this;
    }
    public HomePage Dönüs(String year, String month, String day) {
        driver.findElement(DönüsTarihi).click();

        String dateValue = String.format("//*[@data-testid='day-%s-%s-%s']", year, month, day);
        driver.findElement(By.xpath(dateValue)).click();
        return this;
    }
*/
    public FlightListingPage submit(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(BiletButton));
        button.click();
        return new FlightListingPage(driver);
    }
    public HomePage ensureHotelCheckboxUnchecked() {
        WebElement checkbox = driver.findElement(ListingHotelCheck);

        if (checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }
    public HomePage Nicosia(){
        driver.findElement(Nereye).click();
        driver.findElement(NicosiaBtn).click();
        return this;
    }
}
