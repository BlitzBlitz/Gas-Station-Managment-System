package com.ikubinfo.Internship;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity
public class InternshipApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(InternshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
