package com.kishore.springboot.it.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kishore.springboot.it.data.StudentJpaRepository;
import com.kishore.springboot.it.dto.StudentDTO;
import com.kishore.springboot.it.entity.StudentEntity;
import com.kishore.springboot.it.mapper.MapStructStudentMapper;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentJpaRepository repository;

//	@Autowired
	private MapStructStudentMapper mapper;

	@Override
	public StudentDTO getStudentById(Long id) {
		Optional<StudentEntity> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new NoSuchElementException("Student with id: " + id + "not present in database!");
		}
		StudentDTO dto = mapper.studentEntityToDTO(optional.get());
		return dto;
	}

	@Override
	public List<StudentDTO> getAllStudents() {
		return null;
	}

	@Override
	public StudentDTO addStudent(StudentDTO dto) {

		if (dto == null) {
			throw new IllegalArgumentException("DTO object cannot be null");
		}

		StudentEntity entity = mapper.studentDTOToEntity(dto);
		StudentEntity createdStudent = repository.save(entity);
		return mapper.studentEntityToDTO(createdStudent);
	}

}
