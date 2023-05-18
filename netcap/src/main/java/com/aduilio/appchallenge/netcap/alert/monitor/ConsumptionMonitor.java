package com.aduilio.appchallenge.netcap.alert.monitor;

import com.aduilio.appchallenge.netcap.alert.ConsumptionAlert;
import com.aduilio.appchallenge.netcap.alert.ConsumptionAlertReceiver;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import com.aduilio.appchallenge.netcap.service.TrafficService;
import com.aduilio.appchallenge.netcap.util.CompareDataUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * Checks the consumption.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumptionMonitor {

    private final List<ConsumptionAlert> consumptionAlerts;
    private final List<ConsumptionAlertReceiver> consumptionAlertReceivers;

    private final TrafficService trafficService;
    private final SettingsService settingsService;
    private final CompareDataUtil compareDataUtil;

    public void run() {
        try {
            thread.start();
        } catch (Exception e) {
            log.error("Failed to start the consumption monitor.", e);
        }
    }

    Thread thread = new Thread(() -> {
        while (true) {
            checkConsumptionAlert();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    private void checkConsumptionAlert() {
        var thresholdConsumption = settingsService.getThresholdConsumption();
        var monthConsumption = trafficService.consumptionMonth();

        if (!StringUtils.isEmptyOrWhitespace(thresholdConsumption) && compareDataUtil.reachConsumptionValue(monthConsumption, thresholdConsumption)) {
            var message = "There has already been a consumption greater than " + thresholdConsumption + " of the internet plan this month.";

            consumptionAlerts.forEach(alert -> alert.send(message));
            consumptionAlertReceivers.forEach(receiver -> receiver.consumptionAlert(message));
        } else {
            consumptionAlertReceivers.forEach(receiver -> receiver.consumptionAlert(null));
        }
    }
}
