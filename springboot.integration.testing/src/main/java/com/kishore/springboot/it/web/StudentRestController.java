package com.kishore.springboot.it.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/v1/api")
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

	@GetMapping("students")
	public ResponseEntity<List<StudentDTO>> getAllStudent() {
		List<StudentDTO> students = service.getAllStudents();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping("students/{id}")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable("id") Long studentId) {
		StudentDTO student = null;
		try {
			student = service.getStudentById(studentId);
		} catch (NoSuchElementException nse) {
			return new ResponseEntity<>(student, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

}
