package com.example.Bus.Finder.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BusFinderSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(BusFinderSystemApplication.class, args);
	}

}
