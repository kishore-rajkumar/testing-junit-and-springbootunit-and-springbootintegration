package com.kishore.springboot.it;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kishore.springboot.it.web.StudentRestController;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private StudentRestController controller;

	@Test
	void contextLoads() {
		assertNotNull(controller);
	}

}
