package com.aduilio.appchallenge.netcap.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link DateUtil}.
 */
@ExtendWith(MockitoExtension.class)
public class DateUtilTest {

    private final DateUtil dateUtil = new DateUtil();

    @Test
    void startDateTime_withValue_shouldSetHour() {
        var localDate = LocalDate.now();
        var result = dateUtil.startDateTime(localDate);

        assertEquals(result.getDayOfMonth(), localDate.getDayOfMonth());
        assertEquals(result.getMonth(), localDate.getMonth());
        assertEquals(result.getYear(), localDate.getYear());
        assertEquals(result.getHour(), 0);
        assertEquals(result.getMinute(), 0);
        assertEquals(result.getSecond(), 0);
    }

    @Test
    void endDateTime_withValue_shouldSetHour() {
        var localDate = LocalDate.now();
        var result = dateUtil.endDateTime(localDate);

        assertEquals(result.getDayOfMonth(), localDate.getDayOfMonth());
        assertEquals(result.getMonth(), localDate.getMonth());
        assertEquals(result.getYear(), localDate.getYear());
        assertEquals(result.getHour(), 23);
        assertEquals(result.getMinute(), 59);
        assertEquals(result.getSecond(), 59);
    }

    @Test
    void startWeek_withValue_shouldSetSunday() {
        var localDate = LocalDate.now();
        var result = dateUtil.startWeek();

        assertEquals(result.getDayOfWeek(), DayOfWeek.SUNDAY);
        assertTrue(localDate.isAfter(result.toLocalDate()) || localDate.isEqual(result.toLocalDate()));
        assertEquals(result.getHour(), 0);
        assertEquals(result.getMinute(), 0);
        assertEquals(result.getSecond(), 0);
    }

    @Test
    void endWeek_withValue_shouldSetSaturday() {
        var localDate = LocalDate.now();
        var result = dateUtil.endWeek();

        assertEquals(result.getDayOfWeek(), DayOfWeek.SATURDAY);
        assertTrue(localDate.isBefore(result.toLocalDate()) || localDate.isEqual(result.toLocalDate()));
        assertEquals(result.getHour(), 23);
        assertEquals(result.getMinute(), 59);
        assertEquals(result.getSecond(), 59);
    }

    @Test
    void startMonth_withCurrentValue_shouldSetFirstDay() {
        var localDate = LocalDate.now();
        var result = dateUtil.startMonth();

        assertEquals(result.getDayOfMonth(), 1);
        assertEquals(result.getMonth(), localDate.getMonth());
        assertEquals(result.getHour(), 0);
        assertEquals(result.getMinute(), 0);
        assertEquals(result.getSecond(), 0);
    }

    @Test
    void endMonth_withCurrentValue_shouldSetLastDay() {
        var localDate = LocalDate.now();
        var result = dateUtil.endMonth();

        assertEquals(result.getDayOfMonth(), localDate.getMonth().maxLength());
        assertEquals(result.getMonth(), localDate.getMonth());
        assertEquals(result.getHour(), 23);
        assertEquals(result.getMinute(), 59);
        assertEquals(result.getSecond(), 59);
    }

    @Test
    void startMonth_withValue_shouldSetFirstDay() {
        var localDate = LocalDate.now().minusMonths(2);
        var result = dateUtil.startMonth(localDate);

        assertEquals(result.getDayOfMonth(), 1);
        assertEquals(result.getMonth(), localDate.getMonth());
        assertEquals(result.getHour(), 0);
        assertEquals(result.getMinute(), 0);
        assertEquals(result.getSecond(), 0);
    }

    @Test
    void endMonth_withValue_shouldSetLastDay() {
        var localDate = LocalDate.now().minusMonths(2);
        var result = dateUtil.endMonth(localDate);

        assertEquals(result.getDayOfMonth(), localDate.getMonth().maxLength());
        assertEquals(result.getMonth(), localDate.getMonth());
        assertEquals(result.getHour(), 23);
        assertEquals(result.getMinute(), 59);
        assertEquals(result.getSecond(), 59);
    }
}
