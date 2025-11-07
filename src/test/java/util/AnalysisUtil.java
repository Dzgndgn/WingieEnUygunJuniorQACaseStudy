package util;

import Pages.FlightRow;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.HeatMapChart;
import org.knowm.xchart.HeatMapChartBuilder;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisUtil {

    private static double parsePriceSafe(String raw) {
        if (raw == null) return -1;
        try {
            String cleaned = raw.replaceAll("\\s|TL", "")
                    .replace(".", "")
                    .replace(",", ".");
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            return -1;
        }
    }

    private static long parseDurationMinutesSafe(String raw) {
        if (raw == null) return 0;
        try {
            Matcher onlyMin = Pattern.compile("(?i)(\\d+)\\s*dk").matcher(raw);
            if (onlyMin.find() && raw.toLowerCase().contains("dk") && !raw.toLowerCase().contains("sa")) {
                return Long.parseLong(onlyMin.group(1));
            }
            Matcher hm = Pattern.compile("(\\d+)\\D+(\\d+)").matcher(raw);
            int h = 0, m = 0;
            if (hm.find()) {
                h = Integer.parseInt(hm.group(1));
                m = Integer.parseInt(hm.group(2));
            }
            return h * 60L + m;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int parseHourSafe(String raw) {
        if (raw == null) return -1;
        try {
            if (raw.contains(":")) {
                return Integer.parseInt(raw.split(":")[0].replaceAll("[^0-9]", ""));
            }
            String digits = raw.replaceAll("[^0-9]", "");
            return digits.isEmpty() ? -1 : Integer.parseInt(digits);
        } catch (Exception e) {
            return -1;
        }
    }

    public static Map<String, DoubleSummaryStatistics> statsByAirline(List<FlightRow> rows) {
        Map<String, DoubleSummaryStatistics> map = new TreeMap<>();
        for (FlightRow r : rows) {
            double p = parsePriceSafe(r.price);
            if (p <= 0) continue;
            String key = (r.airline == null ? "" : r.airline);
            map.computeIfAbsent(key, k -> new DoubleSummaryStatistics()).accept(p);
        }
        return map;
    }

    public static List<FlightRow> topCostEffective(List<FlightRow> rows, int topN) {
        List<FlightRow> copy = new ArrayList<>(rows);
        copy.sort(Comparator.comparingDouble(AnalysisUtil::score));
        int end = Math.min(topN, copy.size());
        return new ArrayList<>(copy.subList(0, end));
    }

    private static double score(FlightRow r) {
        double price = parsePriceSafe(r.price);

        String conn = (r.connection == null ? "" : r.connection.toLowerCase());
        double connectionPenalty;
        if (conn.contains("2")) connectionPenalty = 250;
        else if (conn.contains("1")) connectionPenalty = 100;
        else if (conn.contains("aktarmasız") || conn.contains("direct")) connectionPenalty = 0;
        else connectionPenalty = 100;

        double durationPenalty = parseDurationMinutesSafe(r.duration) / 10.0;

        return (price < 0 ? Double.MAX_VALUE : price) + connectionPenalty + durationPenalty;
    }

    public static void plotAirlineStats(Map<String, DoubleSummaryStatistics> stats, String outPng)
            throws IOException {

        List<String> airlines = new ArrayList<>(stats.keySet());
        Collections.sort(airlines);

        List<Double> min = new ArrayList<>();
        List<Double> avg = new ArrayList<>();
        List<Double> max = new ArrayList<>();
        for (String a : airlines) {
            DoubleSummaryStatistics s = stats.get(a);
            min.add(s.getMin());
            avg.add(s.getAverage());
            max.add(s.getMax());
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(900).height(600)
                .title("Price by Airline")
                .xAxisTitle("Airline")
                .yAxisTitle("Price (TRY)")
                .build();

        chart.addSeries("Min", airlines, min);
        chart.addSeries("Avg", airlines, avg);
        chart.addSeries("Max", airlines, max);

        BitmapEncoder.saveBitmap(chart, outPng, BitmapEncoder.BitmapFormat.PNG);
    }

    public static void plotHeatmap(List<FlightRow> rows, String outPng) throws IOException {

        DoubleSummaryStatistics[] byHour = new DoubleSummaryStatistics[24];
        for (int i = 0; i < 24; i++) byHour[i] = new DoubleSummaryStatistics();

        for (FlightRow r : rows) {
            if (r == null) continue;

            int h = -1;
            try {
                String s = (r.departureHour == null) ? "" : r.departureHour;
                h = s.contains(":")
                        ? Integer.parseInt(s.split(":")[0].replaceAll("[^0-9]", ""))
                        : Integer.parseInt(s.replaceAll("[^0-9]", ""));
            } catch (Exception ignored) {}

            double p = parsePriceSafe(r.price);
            if (h >= 0 && h < 24 && p > 0) byHour[h].accept(p);
        }

        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < 24; i++) x.add(i);
        List<Integer> y = Collections.singletonList(0);

        List<Number[]> zTriples = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            double avg = byHour[h].getCount() == 0 ? Double.NaN : byHour[h].getAverage();
            zTriples.add(new Number[]{h, 0, avg});
        }

        HeatMapChart chart = new HeatMapChartBuilder()
                .width(1000).height(220)
                .title("Avg Price by Departure Hour")
                .build();

        chart.addSeries("prices", x, y, zTriples);
        BitmapEncoder.saveBitmap(chart, outPng, BitmapEncoder.BitmapFormat.PNG);
    }
}
