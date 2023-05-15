package com.aduilio.appchallenge.netcap.alert.impl;

import com.aduilio.appchallenge.netcap.alert.ConsumptionAlert;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

/**
 * Implements {@link ConsumptionAlert} to send an SMS.
 */
@Slf4j
@RequiredArgsConstructor
public class SmsConsumptionAlert implements ConsumptionAlert {

    private final SettingsService settingsService;

    @Override
    public void send(String message) {
        if (!StringUtils.isEmptyOrWhitespace(settingsService.getPhoneConsumption())) {
            log.info("Sending an SMS to " + settingsService.getPhoneConsumption() + " with message " + message);
        }
    }
}
