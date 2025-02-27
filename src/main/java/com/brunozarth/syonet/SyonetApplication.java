package com.brunozarth.syonet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SyonetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyonetApplication.class, args);
	}

}
