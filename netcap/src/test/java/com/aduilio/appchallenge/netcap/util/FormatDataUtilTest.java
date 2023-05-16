package com.aduilio.appchallenge.netcap.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link FormatDataUtil}.
 */
@ExtendWith(MockitoExtension.class)
public class FormatDataUtilTest {

    private final FormatDataUtil formatDataUtil = new FormatDataUtil();

    @Test
    void parseToHumanReadable_withGigaValue_shouldReturnGB() {
        var response = formatDataUtil.parseToHumanReadable(3000000000L);
        assertEquals(response, "2.79 GB");
    }

    @Test
    void parseToHumanReadable_withMegaValue_shouldReturnMB() {
        var response = formatDataUtil.parseToHumanReadable(3000000L);
        assertEquals(response, "2.86 MB");
    }

    @Test
    void parseToHumanReadable_withKiloValue_shouldReturnKB() {
        var response = formatDataUtil.parseToHumanReadable(3000L);
        assertEquals(response, "2.93 KB");
    }

    @Test
    void parseToHumanReadable_withByteValue_shouldReturnB() {
        var response = formatDataUtil.parseToHumanReadable(900L);
        assertEquals(response, "900 B");
    }
}
