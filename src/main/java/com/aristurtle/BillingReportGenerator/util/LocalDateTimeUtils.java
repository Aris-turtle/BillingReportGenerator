package com.aristurtle.BillingReportGenerator.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtils {
    public static LocalDateTime getLocalDateTime(long callStart) {
        return Instant.ofEpochMilli(callStart).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long getEpochMilli(LocalDateTime dateFrom) {
        return dateFrom.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
