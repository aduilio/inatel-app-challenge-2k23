package com.aduilio.appchallenge.netcap.util;

import com.aduilio.appchallenge.netcap.entity.TrafficInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to compare human-readable data values.
 */
@Component
public class CompareDataUtil {

    /**
     * Checks if the consumption value is greater than the threshold.
     *
     * @param value     the value
     * @param threshold the threshold
     * @return Boolean
     */
    public boolean reachConsumptionValue(String value, String threshold) {
        var fValue = convertToLong(value);
        var fThreshold = convertToLong(threshold);

        return fValue > fThreshold;
    }

    /**
     * Checks if the usage value is greater than the threshold.
     *
     * @param currentTraffics the traffics
     * @param threshold       the threshold
     * @return boolean
     */
    public List<String> reachUsageValue(List<TrafficInfo> currentTraffics, String threshold) {
        List<String> applications = new ArrayList<>();
        var fThreshold = convertToLong(threshold);
        currentTraffics.forEach(traffic -> {
            var fValue = convertToLong(traffic.getDownload());
            if (fValue > fThreshold) {
                applications.add(traffic.getName());
            }
        });

        return applications;
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
