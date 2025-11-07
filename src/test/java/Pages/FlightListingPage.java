package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FlightListingPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    public FlightListingPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.or( ExpectedConditions.urlContains("/ucak-bileti")));
    }
    final By flightDuration = By.xpath(".//div[@class ='flight-summary-time']//div[@class='summary-duration']//span[@data-testid='departureFlightTime']");
    final By ArrivalTime =By.xpath(".//div[@class ='flight-summary-time']//div[@class='flight-arrival-time']");
    final By DepartureTime =By.xpath(".//div[@class ='flight-summary-time']//div[@class='flight-departure-time']");
    final By Price = By.xpath(".//div[@class='flight-summary-price']//div[@class='summary-average-price']");//dataprice da fiyat var
    final By flightCards = By.xpath("//div[@class='flight-summary-infos']");
    final By airlines = By.xpath(".//div[@class = 'flight-summary-airline']//div[@class ='summary-airline']//div[@class='summary-marketing-airlines ']");
    final By airports = By.xpath(".//div[@class = 'flight-summary-airline']//div[@class ='summary-airports tr']");
    final By flightTime = By.xpath("//div[@class = 'flight-summary']//div[@class ='flight-summary-time']");
    final By departureTimeInfo = By.xpath("//.//div[@class = 'flight-list-body']//div[@id='flight-0']//div[@class ='flight-summary-infos']//div[@class ='flight-departure-time']");
    final By flightAirlineInfo = By.xpath("//div[@class = 'flight-list-body']//div[@id='flight-0']//div[@class ='flight-summary-infos']//div[@class ='summary-marketing-airlines ']");
    final By SelectandContinueBtn = By.xpath("//div[@class = 'flight-list-body']//div[@id='flight-0']//button[@data-testid='providerSelectBtn']//div[@class ='btn-wrapper']");
    final By FlightSelectBtn = By.xpath("//div[@class = 'flight-list-body']//div[@id='flight-0']//button[@class='action-select-btn tr btn btn-success btn-sm']");
    final By FlightListBody = By.xpath("//div[@class = 'flight-list-body']");
    final By FlightListItems = By.xpath("//div[@class='flight-list-body']//div[contains(@class, ' domestic detail-close')]");
    final By AscendingPriceBtn = By.xpath("//div[@class ='sort-buttons search__filter_sort-PRICE_ASC active'] ");
    final By TKCheckbox = By.xpath("//span[@class = 'search__filter_airlines-TK --span-3']");
    final By AirlinesDropdown = By.xpath("//div[@class ='ctx-filter-airline card-header']");
    final By TimeFilterDropdown = By.xpath("//div[contains(@class, 'ctx-filter-departure-return-time') and contains(@class, 'card-header')]");
    final By WholeSlide = (By.xpath("//div[@data-testid='departureDepartureTimeSlider']//div[contains(@class, 'rc-slider-rail')]"));
    final By leftSlider = By.xpath("//div[@data-testid='departureDepartureTimeSlider']//div[contains(@class, 'rc-slider-handle rc-slider-handle-1')]");
    final By rightSlider = By.xpath("//div[@data-testid='departureDepartureTimeSlider']//div[contains(@class, 'rc-slider-handle rc-slider-handle-2')]");
    final By timeFilterText = By.xpath("(//div[contains(@class,'filter-slider-content')])[1]");
    public boolean isLoaded(){
        return driver.getCurrentUrl().contains("ucak-bileti");
    }
    public FlightListingPage clickDropdown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        WebElement timefilter = wait.until(ExpectedConditions.elementToBeClickable(TimeFilterDropdown));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", timefilter);
        wait.until(ExpectedConditions.elementToBeClickable(TimeFilterDropdown)).click();
        //driver.findElement(TimeFilterDropdown).click();
        return this;
    }
    public FlightListingPage ArrangeTimeFilter(int startHour, int endHour) {

        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(WholeSlide));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", slider);


        WebElement rail  = driver.findElement(WholeSlide);
        WebElement left  = wait.until(ExpectedConditions.elementToBeClickable(leftSlider));
        WebElement right = wait.until(ExpectedConditions.elementToBeClickable(rightSlider));


        startHour = Math.max(0, Math.min(24, startHour));
        endHour   = Math.max(0, Math.min(24, endHour));


        Rectangle rRail = rail.getRect();
        int railLeft  = rRail.getX();
        int railWidth = rRail.getWidth();

        int targetStartX = railLeft + (int)Math.round(railWidth * (startHour / 24.0));
        int targetEndX   = railLeft + (int)Math.round(railWidth * (endHour   / 24.0));


        Rectangle rLeft  = left.getRect();
        Rectangle rRight = right.getRect();
        int leftCenterX  = rLeft.getX()  + rLeft.getWidth()/2;
        int rightCenterX = rRight.getX() + rRight.getWidth()/2;

        // 6) Gerekli göreli delta (Initial)
        int dxRight = targetEndX   - rightCenterX;
        int dxLeft  = targetStartX - leftCenterX;


        Actions a = new Actions(driver);


        a.moveToElement(right).pause(Duration.ofMillis(80))
                .clickAndHold().pause(Duration.ofMillis(80))
                .moveByOffset(dxRight, 0).pause(Duration.ofMillis(80))
                .release().perform();


        try { Thread.sleep(500); } catch (InterruptedException ignored) {}


        rLeft = left.getRect();
        leftCenterX = rLeft.getX() + rLeft.getWidth()/2;
        dxLeft = targetStartX - leftCenterX;


        a.moveToElement(left).pause(Duration.ofMillis(80))
                .clickAndHold().pause(Duration.ofMillis(80))
                .moveByOffset(dxLeft, 0).pause(Duration.ofMillis(80))
                .release().perform();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        return this;
    }

    public  boolean isTimeFilterCorrect(String text){
        if(driver.findElement(timeFilterText).getText().equals(text))
            return true;
        else
            return false;
    }
    public FlightListingPage openAirlineDropdownAndSelect(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].scrollIntoView({block:'center'});", timefilter);
        driver.findElement(AirlinesDropdown).click();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        driver.findElement(TKCheckbox).click();
        return this;
    }
    public FlightListingPage PriceToHigher(){
        driver.findElement(AscendingPriceBtn).click();
        return this;
    }
    public boolean verifyFlightResults(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(FlightListBody));
        List<WebElement> ListItems= driver.findElements(FlightListItems);
        int result = ListItems.size();
        if(result > 0)
            return true;
        else
            return false;
    }
    public PassengerDetailsPage selectFlight(){
        driver.findElement(FlightSelectBtn).click();

        Assert.assertTrue(driver.findElement(SelectandContinueBtn).isEnabled());
        driver.findElement(SelectandContinueBtn).click();
        return new PassengerDetailsPage(driver);
    }
    public  String getAirlineName(){
        return driver.findElement(flightAirlineInfo).getText();
    }
    public String getDepartureTimeInfo(){
        return driver.findElement(departureTimeInfo).getText();
    }
    public List<FlightRow> collectAllCards(){
        List<WebElement> cards =driver.findElements(flightCards);
        List<FlightRow> rows = new ArrayList<>();
        for (WebElement element : cards){
            String dep = element.findElement(DepartureTime).getText();
            String arr = element.findElement(ArrivalTime).getText();
            String air = element.findElement(airlines).getText();
            String dur = element.findElement(flightDuration).getText();
            String pri =element.findElement(Price).getAttribute("data-price");
            String con = element.findElement(airports).getText();

            rows.add(FlightRow.from(dep,arr,air,pri,con,dur));
        }
        return rows;
    }
    public void waitCards(){
        new WebDriverWait(driver,Duration.ofSeconds(20)).until(ExpectedConditions.numberOfElementsToBeMoreThan(flightCards,0));
    }
}
