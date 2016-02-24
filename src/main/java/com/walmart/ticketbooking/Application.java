package com.walmart.ticketbooking;

import static com.walmart.ticketbooking.consts.ServiceConsts.MODE_TEST;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.walmart.ticketbooking.config.ApplicationConfig;

public class Application {

	private static Logger log = Logger.getLogger(Application.class);

	private static ApplicationContext ctx;

	public static void main(String [] args) {
		@SuppressWarnings("rawtypes")
		PropertySource ps = new SimpleCommandLinePropertySource(args);
		String mode = (String) ps.getProperty("mode");
		if (args.length == 0  || !MODE_TEST.equalsIgnoreCase(mode)) {
			SpringApplication application = new SpringApplication(ApplicationConfig.class);
			ctx = application.run(args);
		} else {
			log.info("run in command line test mode");
			
		}
	}
}
