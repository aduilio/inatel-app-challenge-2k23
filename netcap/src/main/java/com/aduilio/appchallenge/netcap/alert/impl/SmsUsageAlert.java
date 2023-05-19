package com.aduilio.appchallenge.netcap.alert.impl;

import com.aduilio.appchallenge.netcap.alert.UsageAlert;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

/**
 * Implements {@link UsageAlert} to send an SMS.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmsUsageAlert implements UsageAlert {

    private final SettingsService settingsService;

    @Override
    public void send(String message) {
        if (!StringUtils.isEmptyOrWhitespace(settingsService.getPhoneConsumption())) {
            log.info("Sending a SMS to " + settingsService.getPhoneConsumption() + " with message " + message);
        }
    }
}
