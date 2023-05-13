package com.aduilio.appchallenge.netcap.connector.impl;

import com.aduilio.appchallenge.netcap.connector.Connector;
import com.aduilio.appchallenge.netcap.connector.handler.SocketConnectionHandler;
import com.aduilio.appchallenge.netcap.connector.observer.SocketConnectionObserver;
import com.aduilio.appchallenge.netcap.properties.SocketConnectionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Implements {@link Connector} to perform a Socket connection.
 */
@Component
@Slf4j
public class SocketConnector implements Connector, SocketConnectionObserver {

    @Autowired
    SocketConnectionProperties properties;

    @Override
    public void connect() {
        try {
            new SocketConnectionHandler(properties.getHost(), properties.getPort(), this).start();
        } catch (IOException e) {
            log.error("Failed to connect to the socket server.", e);
        }
    }

    @Override
    public void traffic(String traffic) {
        System.out.println("\n\nCONTENT 1: " + traffic);
    }
}
