package com.bookshop;

import com.bookshop.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackageClasses = {Config.class})
public class Application{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
