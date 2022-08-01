package com.wamisoftware.candidatetest.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateTimeUtils {

    /**
     * Util method calculates difference between two time zones in seconds.
     *
     * @param timeZone1 the start time zone
     * @param timeZone2 the end time zone
     * @return {@code Long} differences between time zones in seconds
     */
    public static Long diffBetweenTimeZonesInSeconds(String timeZone1, String timeZone2) {
        LocalDate today = LocalDate.now();
        return Duration.between(today.atStartOfDay(ZoneId.of(timeZone1)),
                today.atStartOfDay(ZoneId.of(timeZone2))).getSeconds();
    }
}
