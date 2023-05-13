package com.aduilio.appchallenge.netcap;

import com.aduilio.appchallenge.netcap.connector.Connector;
import com.aduilio.appchallenge.netcap.properties.SocketConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(SocketConnectionProperties.class)
public class NetCapApplication {

	@Autowired
	private Connector connector;

	public static void main(String[] args) {
		SpringApplication.run(NetCapApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startCollectTraffic() {
		connector.connect();
	}
}
