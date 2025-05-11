package com.tyrdanov.bank_card_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BankCardManagementSystemApplication {
	public static void main(String[] args) {
		Dotenv
				.configure()
				.ignoreIfMissing()
				.load()
				.entries()
				.forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(BankCardManagementSystemApplication.class, args);
	}

}
