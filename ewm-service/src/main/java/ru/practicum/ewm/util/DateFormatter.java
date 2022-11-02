package ru.practicum.ewm.util;

import java.time.LocalDateTime;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

/**
 * Final class which format date
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public final class DateFormatter {
    /**
     * Method which format LocalDateTime to String with formatter
     *
     * @param time
     * @return String
     */
    public static String formatDate(LocalDateTime time) {
        return time.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * Method which parse LocalDateTime from String with formatter
     *
     * @param date
     * @return
     */
    public static LocalDateTime stringToDate(String date) {
        return LocalDateTime.parse(date, DEFAULT_DATE_FORMATTER);
    }
}
