package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.entity.Settings;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.prefs.Preferences;

/**
 * Provides methods to access the settings.
 */
@Component
public class SettingsService {

    private static final String THRESHOLD_CONSUMPTION = "THRESHOLD_CONSUMPTION";
    private static final String EMAIL_CONSUMPTION = "EMAIL_CONSUMPTION";
    private static final String PHONE_CONSUMPTION = "PHONE_CONSUMPTION";
    private static final String THRESHOLD_USAGE = "THRESHOLD_USAGE";
    private static final String EMAIL_USAGE = "EMAIL_USAGE";
    private static final String PHONE_USAGE = "PHONE_USAGE";

    private final Preferences preferences;

    /**
     * Creates a {@link SettingsService}.
     */
    public SettingsService() {
        preferences = Preferences.userRoot().node(this.getClass().getName());
    }

    /**
     * Returns all user settings.
     *
     * @return Settings
     */
    public Settings getSettings() {
        return Settings.builder()
                .thresholdConsumption(getThresholdConsumption())
                .emailConsumption(getEmailConsumption())
                .phoneConsumption(getPhoneConsumption())
                .thresholdUsage(getThresholdUsage())
                .emailUsage(getEmailUsage())
                .phoneUsage(getPhoneUsage())
                .build();
    }

    /**
     * Saves all user settings.
     *
     * @param settings to be saved
     */
    public void saveSettings(Settings settings) {
        saveThresholdConsumption(settings.getThresholdConsumption());
        saveEmailConsumption(settings.getEmailConsumption());
        savePhoneConsumption(settings.getPhoneConsumption());
        saveThresholdUsage(settings.getThresholdUsage());
        saveEmailUsage(settings.getEmailUsage());
        savePhoneUsage(settings.getPhoneUsage());
    }

    /**
     * Saves the threshold consumption setting value.
     *
     * @param value to be saved
     */
    public void saveThresholdConsumption(String value) {
        if (!StringUtils.isEmptyOrWhitespace(value)) {
            preferences.put(THRESHOLD_CONSUMPTION, value);
        }
    }

    /**
     * Returns the threshold consumption setting value.
     *
     * @return String
     */
    public String getThresholdConsumption() {
        return preferences.get(THRESHOLD_CONSUMPTION, "");
    }

    /**
     * Saves the email consumption setting value.
     *
     * @param value to be saved
     */
    public void saveEmailConsumption(String value) {
        if (!StringUtils.isEmptyOrWhitespace(value)) {
            preferences.put(EMAIL_CONSUMPTION, value);
        }
    }

    /**
     * Returns the email consumption setting value.
     *
     * @return String
     */
    public String getEmailConsumption() {
        return preferences.get(EMAIL_CONSUMPTION, "");
    }

    /**
     * Saves the phone consumption setting value.
     *
     * @param value to be saved
     */
    public void savePhoneConsumption(String value) {
        if (!StringUtils.isEmptyOrWhitespace(value)) {
            preferences.put(PHONE_CONSUMPTION, value);
        }
    }

    /**
     * Returns the phone consumption setting value.
     *
     * @return String
     */
    public String getPhoneConsumption() {
        return preferences.get(PHONE_CONSUMPTION, "");
    }

    /**
     * Saves the threshold usage setting value.
     *
     * @param value to be saved
     */
    public void saveThresholdUsage(String value) {
        if (!StringUtils.isEmptyOrWhitespace(value)) {
            preferences.put(THRESHOLD_USAGE, value);
        }
    }

    /**
     * Returns the threshold usage setting value.
     *
     * @return String
     */
    public String getThresholdUsage() {
        return preferences.get(THRESHOLD_USAGE, "");
    }

    /**
     * Saves the email usage setting value.
     *
     * @param value to be saved
     */
    public void saveEmailUsage(String value) {
        if (!StringUtils.isEmptyOrWhitespace(value)) {
            preferences.put(EMAIL_USAGE, value);
        }
    }

    /**
     * Returns the email usage setting value.
     *
     * @return String
     */
    public String getEmailUsage() {
        return preferences.get(EMAIL_USAGE, "");
    }

    /**
     * Saves the phone usage setting value.
     *
     * @param value to be saved
     */
    public void savePhoneUsage(String value) {
        if (!StringUtils.isEmptyOrWhitespace(value)) {
            preferences.put(PHONE_USAGE, value);
        }
    }

    /**
     * Returns the phone usage setting value.
     *
     * @return String
     */
    public String getPhoneUsage() {
        return preferences.get(PHONE_USAGE, "");
    }
}
