package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd MMM yyyy HH:mm", Locale.ROOT);
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
        String[] date = parse.split(" ");
        String dateString = null;
        if (date[0].equals("сегодня")) {
            LocalDate now = LocalDate.now();
            String[] day = now.format(formatter).split(" ");
            dateString = day[0] + " " + day[1] + " " + day[2] + " " + date[3];
        } else if (date[0].equals("вчера")) {
            LocalDate now = LocalDate.now().minusDays(1);
            String[] day = now.format(formatter).split(" ");
            dateString = day[0] + " " + day[1] + " " + day[2] + " " + date[3];
        } else {
            date[1] = MONTHS.get(date[1]);
            dateString = date[0] + " " + date[1] + " " + date[2] + " " + date[3];
        }
        return LocalDateTime.parse(dateString, formatter);
    }
}