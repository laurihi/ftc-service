package com.github.laurihi.ftc.ftcservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.github.laurihi.ftc")
public class FtcServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtcServiceApplication.class, args);
	}

}

