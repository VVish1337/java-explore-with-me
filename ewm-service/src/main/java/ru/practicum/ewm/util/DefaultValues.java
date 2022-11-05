package ru.practicum.ewm.util;

import java.time.format.DateTimeFormatter;

/**
 * Class which contains default values
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public final class DefaultValues {
    public static final String DEFAULT_FROM_VALUE = "0";
    public static final String DEFAULT_SIZE_VALUE = "10";
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter
            .ofPattern(DEFAULT_DATE_TIME_PATTERN);
    public static final String CATEGORY_NOT_FOUND = "Category not found id:";
    public static final String EVENT_NOT_FOUND = "Event not found id:";
    public static final String USER_NOT_FOUND = "User not found id:";
    public static final String COMPILATION_NOT_FOUND = "Compilation not found id:";
    public static final String PARTICIPANT_NOT_FOUND = "Participant request not found id:";
    public static final String WRONG_DATE = "The event cannot be earlier than 2 hours from the current time";
    public static final String PENDING_ERROR = "Only pending events can be changed";
    public static final String USER_NOT_OWNER = "User not owner of event";
    public static final String PARTICIPANT_LIMIT = "Participant limit is reached";
}