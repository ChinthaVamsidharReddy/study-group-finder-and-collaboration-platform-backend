package com.infy.project;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		 TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
	        System.out.println("JVM TimeZone set to: " + TimeZone.getDefault().getID());

		SpringApplication.run(Application.class, args);
	}

}
