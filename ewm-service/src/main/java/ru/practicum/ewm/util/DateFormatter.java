package ru.practicum.ewm.util;

import java.time.LocalDateTime;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

public final class DateFormatter {
    public static String formatDate(LocalDateTime time){
        return time.format(DEFAULT_DATE_FORMATTER);
    }

    public static LocalDateTime stringToDate(String date){
        return LocalDateTime.parse(date,DEFAULT_DATE_FORMATTER);
    }
}
