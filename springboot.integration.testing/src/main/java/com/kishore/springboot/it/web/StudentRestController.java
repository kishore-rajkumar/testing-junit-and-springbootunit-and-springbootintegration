package com.kishore.springboot.it.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kishore.springboot.it.dto.StudentDTO;
import com.kishore.springboot.it.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class StudentRestController {

	@Autowired
	private StudentService service;

	@GetMapping
	public String welcome() {
		return "Hello, welcome to the workld of testing!";
	}

	@PostMapping("student")
	public ResponseEntity<Void> createStudent(@Valid @RequestBody StudentDTO dto) {

		StudentDTO created = service.addStudent(dto);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}

}
