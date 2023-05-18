package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.alert.ConsumptionAlertReceiver;
import com.aduilio.appchallenge.netcap.alert.UsageAlertReceiver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Provides methods to receive and store alerts.
 */
@Service
@Getter
@RequiredArgsConstructor
public class AlertService implements ConsumptionAlertReceiver, UsageAlertReceiver {

    private String consumptionAlert;
    private String usageAlert;

    @Override
    public void consumptionAlert(String message) {
        consumptionAlert = message;
    }

    @Override
    public void usageAlert(String message) {
        usageAlert = message;
    }
}
