package com.uniovi.nmapgui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NMapGuiApplication extends AsyncConfigurerSupport {

	public static ConfigurableApplicationContext mainExec(String[] args) {
		return SpringApplication.run(NMapGuiApplication.class, args);
	}
	
	public static void main(String[] args) {
		mainExec(args);
	}
}
