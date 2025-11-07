package Pages;

public class FlightRow {
    public String departure;
    public String arrival;
    public String airline;
    public String price;
    public String connection;
    public String duration;
    public String departureHour;

    public static FlightRow from(String dep, String arr, String air, String pr, String con, String dur) {
        FlightRow r = new FlightRow();
        r.departure = dep;
        r.arrival   = arr;
        r.airline   = air;
        r.price     = (pr);
        r.connection= con;
        r.duration  = (dur);
        r.departureHour = (dep);
        return r;
    }
}
