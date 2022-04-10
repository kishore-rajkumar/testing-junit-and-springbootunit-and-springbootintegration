package com.kishore.springboot.it.web;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kishore.springboot.it.dto.StudentDTO;
import com.kishore.springboot.it.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = StudentRestController.class)
public class StudentControllerUnitTest {

	private final String APP_URI_PREFIX = "/v1/api/";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService mockService;

	@Autowired
	private ObjectMapper objectMapper;

	// 1. Verifying HTTP Request Matching
	@Test
	void test_default_http_get_request_returns200() throws Exception {
		mockMvc.perform(get(APP_URI_PREFIX)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, welcome to the workld of testing!")));
	}

	/*
	 * Tests -> 1. erifying HTTP Request Matching/Valid Request Url 2. Valid
	 * ContentType 3. Returns 201(Created)
	 */
	@Test
	void testAddStudent_whenValidUrlAndMethodAndContentType_thenReturns201() throws Exception {

		// dto to be added
		StudentDTO dto = StudentDTO.builder().name("Json Mamoa").rollNo("928822").build();

		// mock service call
		when(mockService.addStudent(dto)).thenReturn(StudentDTO.builder().id(Long.valueOf(100022)).build());

		mockMvc.perform(post(APP_URI_PREFIX + "student").content(objectMapper.writeValueAsString(dto))
				.contentType("application/json")).andExpect(status().isCreated());
	}

	/*
	 * Tests -> 1. Valid Response Created Url
	 */
	@Test
	void testAddStudent_validCreatedURI() throws Exception {

		// dto to be added
		StudentDTO dto = StudentDTO.builder().name("Json Mamoa").rollNo("928822").build();

		// mock service student DTO return
		StudentDTO mockServiceDto = StudentDTO.builder().id(Long.valueOf(100022)).build();

		// mock service call
		when(mockService.addStudent(dto)).thenReturn(mockServiceDto);

		MvcResult result = mockMvc.perform(post(APP_URI_PREFIX + "student")
				.content(objectMapper.writeValueAsString(dto)).contentType("application/json")).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// assert created student uri contains valid id
		assert (response.getHeader(HttpHeaders.LOCATION)).contains("student/" + mockServiceDto.getId());

	}

	// 3. Verifying Input Validation
	@Test
	void testAddStudent_validateIput_WHEN_NULL_RETURN_BADREQUEST() throws Exception {

		// dto to be added
		StudentDTO dtoWithNullValues = StudentDTO.builder().build();

		mockMvc.perform(post(APP_URI_PREFIX + "student").content(objectMapper.writeValueAsString(dtoWithNullValues))
				.contentType("application/json")).andExpect(status().isBadRequest());
	}

	// Verifying Business Logic Calls
	@Test
	void testAddStudent_whenValidInput_thenMapsToBusinessModel() throws Exception {

		// dto to be added
		StudentDTO dto = StudentDTO.builder().name("Json Mamoa").rollNo("928822").build();

		// mock service call
		when(mockService.addStudent(dto)).thenReturn(StudentDTO.builder().id(Long.valueOf(100022)).build());

		mockMvc.perform(post(APP_URI_PREFIX + "student").content(objectMapper.writeValueAsString(dto))
				.contentType("application/json")).andExpect(status().isCreated());

		ArgumentCaptor<StudentDTO> captor = ArgumentCaptor.forClass(StudentDTO.class);
		verify(mockService, times(1)).addStudent(captor.capture());
		assertEquals(captor.getValue().getName(), dto.getName());
		assertEquals(captor.getValue().getRollNo(), dto.getRollNo());
	}

	// Verifying HTTP Request Matching
	@Test
	void testGetAllStudent_default_http_get_request_returns200() throws Exception {
		mockMvc.perform(get(APP_URI_PREFIX + "students")).andDo(print()).andExpect(status().isOk());
	}

	// Verifying EMPTY_LIST RETURN
	@Test
	void testGetAllStudent_EMPTY_LIST_WHEN_NO_STUDENTS() throws Exception {
		// mock service call - EMPTY LIST RETURN
		when(mockService.getAllStudents()).thenReturn(new ArrayList<>());

		MvcResult result = mockMvc.perform(get(APP_URI_PREFIX + "students")).andExpect(status().isOk()).andReturn();
		List<StudentDTO> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<StudentDTO>>() {
				});
		assertEquals(actual.size(), 0);
	}

	// Verifying LIST OF SIZE 5 RETURN
	@Test
	void testGetAllStudent_STUDENT_LIST_WHEN_THERE_ARE_STUDENTS() throws Exception {
		List<StudentDTO> mockList = new ArrayList<StudentDTO>();
		mockList.add(StudentDTO.builder().build());
		mockList.add(StudentDTO.builder().build());
		mockList.add(StudentDTO.builder().build());
		mockList.add(StudentDTO.builder().build());
		mockList.add(StudentDTO.builder().build());

		// mock service call - EMPTY LIST RETURN
		when(mockService.getAllStudents()).thenReturn(mockList);

		MvcResult result = mockMvc.perform(get(APP_URI_PREFIX + "students")).andExpect(status().isOk()).andReturn();
		List<StudentDTO> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<StudentDTO>>() {
				});
		assertEquals(actual.size(), mockList.size());
	}

	// Verifying BY ID RETURN
	@Test
	void testGetStudentById() throws Exception {

		StudentDTO mockStudent = StudentDTO.builder().id(Long.valueOf(100)).name("John Doe").rollNo("100RE").build();
		// mock service call - EMPTY LIST RETURN
		when(mockService.getStudentById(Long.valueOf(100))).thenReturn(mockStudent);

		MvcResult result = mockMvc.perform(get(APP_URI_PREFIX + "students/100")).andExpect(status().isOk()).andReturn();

		StudentDTO response = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<StudentDTO>() {
				});

		assertEquals(mockStudent, response);
	}

	// Verifying BY INVALID ID RETURN 404
	@Test
	void testGetStudentById_INVALID_ID_404_RETURNED() throws Exception {

		// mock service call - EMPTY LIST RETURN
		when(mockService.getStudentById(any())).thenThrow(NoSuchElementException.class);

		mockMvc.perform(get(APP_URI_PREFIX + "students/9829")).andExpect(status().isNotFound());

	}

}
