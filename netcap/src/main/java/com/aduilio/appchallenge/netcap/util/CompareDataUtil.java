package com.aduilio.appchallenge.netcap.util;

import org.springframework.stereotype.Component;

/**
 * Utility class to compare human-readable data values.
 */
@Component
public class CompareDataUtil {

    public boolean reachValue(String value, String threshold) {
        Float fValue = convertToLong(value);
        Float fThreshold = convertToLong(threshold);

        return fValue > fThreshold;
    }

    private Float convertToLong(String value) {
        if (value.contains("GB")) {
            return Float.parseFloat(value.replace("GB", "").replace(" ", "")) * 1024L * 1024L * 1024L;
        } else if (value.contains("MB")) {
            return Float.parseFloat(value.replace("MB", "").replace(" ", "")) * 1024L * 1024L;
        } else if (value.contains("KB")) {
            return Float.parseFloat(value.replace("KB", "").replace(" ", "")) * 1024L;
        } else {
            return Float.parseFloat(value.replace("B", "").replace(" ", ""));
        }
    }
}
