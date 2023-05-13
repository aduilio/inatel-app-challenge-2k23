package com.aduilio.appchallenge.netcap;

import com.aduilio.appchallenge.netcap.connector.Connector;
import com.aduilio.appchallenge.netcap.properties.SocketConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
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

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("NetCap-");
		executor.initialize();
		return executor;
	}
}
