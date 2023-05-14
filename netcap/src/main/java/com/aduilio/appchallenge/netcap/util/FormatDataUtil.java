package com.aduilio.appchallenge.netcap.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

/**
 * Utility class to format the data values.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FormatDataUtil {

    private static final long BYTE = 1L;
    private static final long KB = BYTE << 10;
    private static final long MB = KB << 10;
    private static final long GB = MB << 10;

    private static final DecimalFormat DEC_FORMAT = new DecimalFormat("#.##");

    /**
     * Parse the data to a human-readable format with B, KB, MB or GB.
     *
     * @param value to be parsed
     * @return String
     */
    public String parseToHumanReadable(Long value) {
        if (value != null) {
            if (value >= GB) {
                return formatSize(value, GB, "GB");
            } else if (value >= MB) {
                return formatSize(value, MB, "MB");
            } else if (value >= KB) {
                return formatSize(value, KB, "KB");
            }

            return formatSize(value, BYTE, "B");
        }

        return "0,00 B";
    }

    private String formatSize(long size, long divider, String unitName) {
        return DEC_FORMAT.format((double) size / divider) + " " + unitName;
    }
}
