package org.example.javafinalproject.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'dd-MM-yyyy'.");
        }
    }
}
