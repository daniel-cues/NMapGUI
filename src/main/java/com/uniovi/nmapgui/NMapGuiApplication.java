package com.uniovi.nmapgui;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class NMapGuiApplication extends AsyncConfigurerSupport {

	public static ConfigurableApplicationContext mainExec(String[] args) {
		return SpringApplication.run(NMapGuiApplication.class, args);
	}
	
	public static void main(String[] args) {
		mainExec(args);
	}
	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("NMap-Thread-");
        executor.initialize();
        return executor;
    }
}
