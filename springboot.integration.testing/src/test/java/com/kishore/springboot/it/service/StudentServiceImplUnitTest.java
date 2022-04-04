package com.kishore.springboot.it.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kishore.springboot.it.data.StudentJpaRepository;
import com.kishore.springboot.it.dto.StudentDTO;
import com.kishore.springboot.it.entity.StudentEntity;
import com.kishore.springboot.it.mapper.MapStructStudentMapper;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplUnitTest {

	@Mock
	private StudentJpaRepository mockRepo;

	@Mock
	private MapStructStudentMapper mockMapper;

	@InjectMocks
	// create object
	private StudentServiceImpl undetTest;

	@Test
	void testGetStudentById() {
		// mock DB student entity
		StudentEntity entity = StudentEntity.builder().id(Long.valueOf(100001)).name("Json Roy").rollNo("25012")
				.build();

		// mock data call
		when(mockRepo.findById(Long.valueOf(100001))).thenReturn(Optional.of(entity));
		// mock mapper operation
		when(mockMapper.studentEntityToDTO(entity))
				.thenReturn(StudentDTO.builder().id(Long.valueOf(100001)).name("Json Roy").rollNo("25012").build());

		// set expectation
		StudentDTO expected = StudentDTO.builder().id(Long.valueOf(100001)).name("Json Roy").rollNo("25012").build();

		// call method under test and get actual/result
		StudentDTO actual = undetTest.getStudentById(Long.valueOf(100001));

		// compare result
		assertEquals(expected, actual);
	}

	@Test
	void testGetStudentById_WHEN_NOT_PRESENT_THROW_NO_SUCH_ELEMENT_EXCEPTION() {

		// mock data call for null entity returned
		when(mockRepo.findById(Long.valueOf(44441))).thenReturn(Optional.ofNullable(null));

		// call method under test and compare expectation
		assertThrows(NoSuchElementException.class, () -> undetTest.getStudentById(Long.valueOf(44441)));

	}

	@Test
	void testAddStudent() {
		
		StudentDTO dto = StudentDTO.builder().name("Alpachino").rollNo("986658").build();
		StudentEntity entity = StudentEntity.builder().id(Long.valueOf(40001)).name("Alpachino").rollNo("986658")
				.build();
		// mock repository save operation
		when(mockRepo.save(any())).thenReturn(entity);

		// mock mapper operation
		when(mockMapper.studentEntityToDTO(entity))
				.thenReturn(StudentDTO.builder().id(entity.getId()).name("Alpachino").rollNo("986658").build());

		StudentDTO actual = undetTest.addStudent(dto);

		assertEquals(entity.getId(), actual.getId());
	}
	
	@Test
	void testAddStudent_NULL_DTO_THROW_ILLEGAL_ARGUMENT_EXCEPTION() {
		assertThrows(IllegalArgumentException.class, ()-> undetTest.addStudent(null));
	}


}
