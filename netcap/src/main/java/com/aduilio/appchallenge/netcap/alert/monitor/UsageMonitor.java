package com.aduilio.appchallenge.netcap.alert.monitor;

import com.aduilio.appchallenge.netcap.alert.UsageAlert;
import com.aduilio.appchallenge.netcap.alert.UsageAlertReceiver;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import com.aduilio.appchallenge.netcap.service.TrafficService;
import com.aduilio.appchallenge.netcap.util.CompareDataUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * Checks the usage.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UsageMonitor {

    private final List<UsageAlert> usageAlerts;
    private final List<UsageAlertReceiver> usageAlertReceivers;

    private final TrafficService trafficService;
    private final SettingsService settingsService;
    private final CompareDataUtil compareDataUtil;

    public void run() {
        try {
            thread.start();
        } catch (Exception e) {
            log.error("Failed to start the usage monitor.", e);
        }
    }

    Thread thread = new Thread(() -> {
        while (true) {
            checkUsageAlert();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    private void checkUsageAlert() {
        var thresholdUsage = settingsService.getThresholdUsage();
        var currentTraffics = trafficService.sumCurrentTraffics();

        if (!StringUtils.isEmptyOrWhitespace(thresholdUsage)) {
            var applications = compareDataUtil.reachUsageValue(currentTraffics, thresholdUsage);
            if (applications != null && !applications.isEmpty()) {
                var message = "There are applications consuming more than " + thresholdUsage + ": " + String.join(", ", applications);

                usageAlerts.forEach(alert -> alert.send(message));
                usageAlertReceivers.forEach(receiver -> receiver.usageAlert(message));
            } else {
                usageAlertReceivers.forEach(receiver -> receiver.usageAlert(null));
            }
        }
    }
}
