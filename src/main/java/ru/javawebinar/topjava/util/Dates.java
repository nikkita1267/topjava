package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Dates {
    private Dates() {}

    public static final String PATTERN = "dd.MM.yyyy kk:mm";
    public static final DateTimeFormatter FORMATTER_FOR_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern(PATTERN);

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER_FOR_LOCAL_DATE_TIME);
    }
}
