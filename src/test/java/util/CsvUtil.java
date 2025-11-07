package util;

import Pages.FlightRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvUtil {
    public static void write(String path, List<FlightRow> rows) throws IOException {
        try (var printer = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT
                .withHeader("Departure","Arrival","Airline","Price","Connection","DurationMin","DepHour"))) {
            for (FlightRow r : rows) {

                printer.printRecord(r.departure, r.arrival, r.airline, r.price, r.connection, r.duration, r.departureHour);
            }
        }
    }
}