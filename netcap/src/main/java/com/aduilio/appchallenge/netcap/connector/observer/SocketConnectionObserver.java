package com.aduilio.appchallenge.netcap.connector.observer;

/**
 * Represents an observer to be invoked when a traffic payload is received.
 */
public interface SocketConnectionObserver {

    void traffic(String traffic);
}
