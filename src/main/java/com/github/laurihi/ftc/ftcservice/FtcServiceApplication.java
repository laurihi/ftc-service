package com.github.laurihi.ftc.ftcservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.github.laurihi.ftc")
@EntityScan(
		basePackageClasses = {FtcServiceApplication.class, Jsr310JpaConverters.class}
)
public class FtcServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtcServiceApplication.class, args);
	}

}

