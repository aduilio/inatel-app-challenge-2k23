package com.aduilio.appchallenge.netcap.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Utility class to provide util date methods.
 */
@Component
public class DateUtil {

    /**
     * Sets the midnight hour.
     *
     * @param date the reference date
     * @return LocalDateTime
     */
    public LocalDateTime startDateTime(LocalDate date) {
        return date.atTime(0, 0, 0);
    }

    /**
     * Sets the 23:59:59 hour.
     *
     * @param date the reference date
     * @return LocalDateTime
     */
    public LocalDateTime endDateTime(LocalDate date) {
        return date.atTime(23, 59, 59);
    }

    /**
     * Returns the first day (sunday) of the current week.
     *
     * @return LocalDateTime
     */
    public LocalDateTime startWeek() {
        return LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).withHour(0).withMinute(0).withSecond(0);
    }

    /**
     * Returns the last day (saturday) of the current week.
     *
     * @return LocalDateTime
     */
    public LocalDateTime endWeek() {
        return LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).withHour(23).withMinute(59).withSecond(59);
    }

    /**
     * Returns the first day of the current month.
     *
     * @return LocalDateTime
     */
    public LocalDateTime startMonth() {
        return startMonth(LocalDate.now());
    }

    /**
     * Returns the last day of the current month.
     *
     * @return LocalDateTime
     */
    public LocalDateTime endMonth() {
        return endMonth(LocalDate.now());
    }

    /**
     * Sets to the first day of the month.
     *
     * @return LocalDateTime
     */
    public LocalDateTime startMonth(LocalDate date) {
        return startDateTime(date.withDayOfMonth(1));
    }

    /**
     * Sets to the last day of the month.
     *
     * @return LocalDateTime
     */
    public LocalDateTime endMonth(LocalDate date) {
        return endDateTime(date.with(TemporalAdjusters.lastDayOfMonth()));
    }
}
