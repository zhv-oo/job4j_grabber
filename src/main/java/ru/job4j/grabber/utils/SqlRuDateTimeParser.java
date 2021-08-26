package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd MMM yyyy HH:mm", Locale.ROOT);
    private static DateTimeFormatter format = DateTimeFormatter
            .ofPattern("dd MMM yy", Locale.ROOT);
    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "Jan"),
            Map.entry("фев", "Feb"),
            Map.entry("мар", "Mar"),
            Map.entry("апр", "Apr"),
            Map.entry("май", "May"),
            Map.entry("июн", "Jun"),
            Map.entry("июл", "Jul"),
            Map.entry("авг", "Aug"),
            Map.entry("сен", "Sep"),
            Map.entry("окт", "Oct"),
            Map.entry("ноя", "Nov"),
            Map.entry("дек", "Dec"));

    @Override
    public LocalDateTime parse(String parse) {
        String[] date = parse.replace(",", "").split(" ");
        String dateString = null;
        if (date[0].equals("сегодня")) {
            LocalDate now = LocalDate.now();
            String[] day = now.format(format).split(" ");
            if (day[0].length() == 1) {
                day[0] = "0" + day[0];
            }
            dateString = day[0] + " " + day[1] + " 20" + day[2] + " " + date[1];
        } else if (date[0].equals("вчера")) {
            LocalDate now = LocalDate.now().minusDays(1);
            String[] day = now.format(format).split(" ");
            if (day[0].length() == 1) {
                day[0] = "0" + day[0];
            }
            dateString = day[0] + " " + day[1] + " 20" + day[2] + " " + date[1];
        } else {
            date[1] = MONTHS.get(date[1]);
            if (date[0].length() == 1) {
                date[0] = "0" + date[0];
            }
            dateString = date[0] + " " + date[1] + " 20" + date[2] + " " + date[3];
        }
        return LocalDateTime.parse(dateString, formatter);
    }
}