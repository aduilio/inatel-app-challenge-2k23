package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.alert.ConsumptionAlert;
import com.aduilio.appchallenge.netcap.alert.UsageAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides methods to send alerts.
 */
@Service
@RequiredArgsConstructor
public class AlertService {

    private final List<ConsumptionAlert> consumptionAlerts;
    private final List<UsageAlert> usageAlerts;

    public void sendConsumptionAlerts(String message) {
        consumptionAlerts.forEach(alert -> alert.send(message));
    }

    public void sendUsageAlerts(String message) {
        usageAlerts.forEach(alert -> alert.send(message));
    }
}
