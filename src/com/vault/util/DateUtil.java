package com.vault.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate parse(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr.trim(), FMT);
    }

    public static String format(LocalDate date) {
        return date.format(FMT);
    }
}
