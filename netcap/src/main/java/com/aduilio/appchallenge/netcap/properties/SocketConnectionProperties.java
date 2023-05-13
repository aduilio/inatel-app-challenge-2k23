package com.aduilio.appchallenge.netcap.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bind the user properties regarding the Socket connection.
 */
@Data
@ConfigurationProperties(prefix = "netcap.connection.socket")
public class SocketConnectionProperties {

    private String host;
    private int port;
}
