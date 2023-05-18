package com.aduilio.appchallenge.netcap.alert;

/**
 * Represents to receivers of the usage alert.
 */
public interface UsageAlertReceiver {

    void usageAlert(String message);
}
