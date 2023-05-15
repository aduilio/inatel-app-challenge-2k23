package com.aduilio.appchallenge.netcap.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents the user settings.
 */
@Builder
@Getter
public class Settings {

    private String thresholdConsumption;
    private String emailConsumption;
    private String phoneConsumption;
    private String thresholdUsage;
    private String emailUsage;
    private String phoneUsage;
}
