package tests;

import Pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.AnalysisUtil;
import util.CsvUtil;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;



public class Case1 extends  BaseTest{
    public static double cleanAndParsePrice(String priceString) {

        String cleaned = priceString.replace(".", ""); // Binlik ayırıcıyı kaldır


        cleaned = cleaned.replace(",", ".");

        return Double.parseDouble(cleaned);
    }
    @Test
    public void case1 (){
        HomePage page = new HomePage(driver);
        FlightListingPage flightPage = page.gidisDönüs().fromCity("İstanbul").fromToCity("Ankara").ensureHotelCheckboxUnchecked().submit();
        Assert.assertTrue(flightPage.isLoaded());
        flightPage.clickDropdown().ArrangeTimeFilter(10,18);
        Assert.assertTrue(flightPage.isTimeFilterCorrect("10:00 ile 18:00 arası"));
    }

    public void case2(){
        HomePage page = new HomePage(driver);
        FlightListingPage flightListingPage = page.gidisDönüs().fromCity("İstanbul").fromToCity("Ankara").ensureHotelCheckboxUnchecked().submit();
        Assert.assertTrue(flightListingPage.isLoaded());
        flightListingPage.clickDropdown().ArrangeTimeFilter(10,18).openAirlineDropdownAndSelect().PriceToHigher();
    }

    public void case3(){
        HomePage page = new HomePage(driver);
        FlightListingPage flightListingPage = page.fromCity("İstanbul").fromToCity("Ankara").ensureHotelCheckboxUnchecked().submit();
        Assert.assertTrue(flightListingPage.isLoaded());
        flightListingPage.clickDropdown().ArrangeTimeFilter(10,18);
        String airlineName = flightListingPage.getAirlineName();
        String departureTime = flightListingPage.getDepartureTimeInfo();
        Assert.assertTrue(flightListingPage.isTimeFilterCorrect("10:00 ile 18:00 arası"));
        Assert.assertTrue(flightListingPage.verifyFlightResults());
        Pages.PassengerDetailsPage passengerDetailsPage = flightListingPage.selectFlight();
        Assert.assertTrue(passengerDetailsPage.isLoaded());
        Assert.assertEquals(airlineName,passengerDetailsPage.getAirlineName());
        Assert.assertEquals(departureTime,passengerDetailsPage.getDepartureTime());
        int passengerPriceInfo = (passengerDetailsPage.getPriceInfo());
        Pages.PaymentPage payment =passengerDetailsPage.fillEmail("duzgundogan0@gmail.com").fillNumber("5348404475").fillName("düzgün").fillLastName("doğan").fillBirthday("09","10","2002").fillIdentityNumber("11198141512").gender("").GoToPayment();
        Assert.assertTrue(payment.isLoaded());
        Assert.assertEquals((passengerPriceInfo),(payment.getTotalPrice()));
        payment.fillCardNum("1234567890123456").fillCVV("123").fillCardMonth().fillCardYear().Payment();

    }

    public void case4()throws IOException {
        HomePage page = new HomePage(driver);
        LocalDate date = LocalDate.now().plusDays(14);
        FlightListingPage listingPage = page.fromCity("İstanbul").Nicosia().ensureHotelCheckboxUnchecked().submit();
        listingPage.waitCards();
        List<FlightRow> rows=listingPage.collectAllCards();
        String stamp = DateTimeFormatter.ofPattern("yyyyMMdd").format(date);
        Files.createDirectories(Path.of("out"));
        String csv = "out/flights_" + "İstanbul" + "_" + "Nicosia" + "_" + stamp + ".csv";
        CsvUtil.write(csv, rows);

        var stats = AnalysisUtil.statsByAirline(rows);
        String barPng = "out/airline_stats_" + stamp + ".png";
        AnalysisUtil.plotAirlineStats(stats, barPng);
        System.out.println("Bar chart → " + barPng);


        String heatPng = "out/price_heatmap_" + stamp + ".png";
        AnalysisUtil.plotHeatmap(rows, heatPng);
        System.out.println("Heatmap → " + heatPng);


        var best = AnalysisUtil.topCostEffective(rows, 5);
        best.forEach(b -> System.out.println(
                "BEST → " + b.airline + " | " + b.departure + "→" + b.arrival +
                        " | " + b.price + " | " + b.connection + " | " + b.duration
        ));
    }
}
