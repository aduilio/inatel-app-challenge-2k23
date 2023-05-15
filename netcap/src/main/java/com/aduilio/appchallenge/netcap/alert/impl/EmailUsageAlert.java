package com.aduilio.appchallenge.netcap.alert.impl;

import com.aduilio.appchallenge.netcap.alert.UsageAlert;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

/**
 * Implements {@link UsageAlert} to send an email.
 */
@Slf4j
@RequiredArgsConstructor
public class EmailUsageAlert implements UsageAlert {

    private final SettingsService settingsService;

    @Override
    public void send(String message) {
        if (!StringUtils.isEmptyOrWhitespace(settingsService.getEmailUsage())) {
            log.info("Sending an email to " + settingsService.getEmailUsage() + " with message " + message);
        }
    }
}
