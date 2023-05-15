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
    void reachValue_withBiggerValue_shouldReturnTrue() {
        var response = compareDataUtil.reachValue("5 B", "2B");
        assertTrue(response);
    }

    @Test
    void reachValue_withBiggerMegaValue_shouldReturnTrue() {
        var response = compareDataUtil.reachValue("5MB", "2 MB");
        assertTrue(response);
    }

    @Test
    void reachValue_withBiggerGigaValue_shouldReturnTrue() {
        var response = compareDataUtil.reachValue("5GB", "2 GB");
        assertTrue(response);
    }

    @Test
    void reachValue_withLowerValue_shouldReturnTrue() {
        var response = compareDataUtil.reachValue("2 MB", "2 GB");
        assertFalse(response);
    }
}
