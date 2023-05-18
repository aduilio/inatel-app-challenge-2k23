package com.aduilio.appchallenge.netcap.alert;

/**
 * Represents to receivers of the consumption alert.
 */
public interface ConsumptionAlertReceiver {

    void consumptionAlert(String message);
}
