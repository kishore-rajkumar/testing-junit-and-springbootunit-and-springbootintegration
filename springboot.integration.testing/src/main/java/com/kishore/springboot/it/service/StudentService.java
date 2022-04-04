package com.kishore.springboot.it.service;

import java.util.List;

import com.kishore.springboot.it.dto.StudentDTO;

public interface StudentService {
	
	StudentDTO getStudentById(Long Id);
	
	List<StudentDTO> getAllStudents();
	
	StudentDTO addStudent(StudentDTO dto);

}
