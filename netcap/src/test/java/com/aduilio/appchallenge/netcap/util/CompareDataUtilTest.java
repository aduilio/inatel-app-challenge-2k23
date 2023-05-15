package com.aduilio.appchallenge.netcap.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CompareDataUtil}.
 */
@ExtendWith(MockitoExtension.class)
public class CompareDataUtilTest {

    private final CompareDataUtil compareDataUtil = new CompareDataUtil();

    @Test
    void reachConsumptionValue_withBiggerValue_shouldReturnTrue() {
        assertTrue(compareDataUtil.reachConsumptionValue("5 B", "2B"));
    }

    @Test
    void reachConsumptionValue_withBiggerMegaValue_shouldReturnTrue() {
        assertTrue(compareDataUtil.reachConsumptionValue("5MB", "2 MB"));
    }

    @Test
    void reachConsumptionValue_withBiggerGigaValue_shouldReturnTrue() {
        assertTrue(compareDataUtil.reachConsumptionValue("5GB", "2 GB"));
    }

    @Test
    void reachConsumptionValue_withLowerValue_shouldReturnTrue() {
        assertFalse(compareDataUtil.reachConsumptionValue("2 MB", "2 GB"));
    }
}
