package com.aduilio.appchallenge.netcap.alert.impl;

import com.aduilio.appchallenge.netcap.alert.ConsumptionAlert;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

/**
 * Implements {@link ConsumptionAlert} to send an email.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumptionAlert implements ConsumptionAlert {

    private final SettingsService settingsService;

    @Override
    public void send(String message) {
        if (!StringUtils.isEmptyOrWhitespace(settingsService.getEmailConsumption())) {
            log.info("Sending an email to " + settingsService.getEmailConsumption() + " with message " + message);
        }
    }
}
