package com.kishore.springboot.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.kishore.springboot.it.mapper.MapStructStudentMapper;

@SpringBootApplication
public class ApplicationIT {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationIT.class, args);
	}
}
