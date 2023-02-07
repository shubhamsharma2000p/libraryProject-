package com.shubham.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {

		System.out.println("Application Starting............");
		SpringApplication.run(LibraryApplication.class, args);
		System.out.println("Application Started ............");
	}

}
