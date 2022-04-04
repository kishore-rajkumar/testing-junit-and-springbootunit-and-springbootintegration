package com.kishore.springboot.it.web;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kishore.springboot.it.dto.StudentDTO;
import com.kishore.springboot.it.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = StudentRestController.class)
public class StudentControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService mockService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test_default_http_get_request_returns200() throws Exception {
		mockMvc.perform(get("/v1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, welcome to the workld of testing!")));
	}

	/*
	 * Tests -> 1. Valid Request Url 2. Valid ContentType 3. Returns 201(Created)
	 */
	@Test
	void testAddStudent_whenValidUrlAndMethodAndContentType_thenReturns201() throws Exception {

		// dto to be added
		StudentDTO dto = StudentDTO.builder().name("Json Mamoa").rollNo("928822").build();

		// mock service call
		when(mockService.addStudent(dto)).thenReturn(StudentDTO.builder().id(Long.valueOf(100022)).build());

		mockMvc.perform(
				post("/v1/student").content(objectMapper.writeValueAsString(dto)).contentType("application/json"))
				.andExpect(status().isCreated());
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

		MvcResult result = mockMvc.perform(
				post("/v1/student").content(objectMapper.writeValueAsString(dto)).contentType("application/json"))
				.andReturn();

		MockHttpServletResponse response = result.getResponse();

		// assert created student uri contains valid id
		assert (response.getHeader(HttpHeaders.LOCATION)).contains("student/" + mockServiceDto.getId());

	}

	/**
	 * Tests -> Null RequestBody Returns BADREQUEST response
	 */
	@Test
	void testAddStudent_validateIput_WHEN_NULL_RETURN_BADREQUEST() throws Exception {

		// dto to be added
		StudentDTO dtoWithNullValues = StudentDTO.builder().build();

		mockMvc.perform(post("/v1/student").content(objectMapper.writeValueAsString(dtoWithNullValues))
				.contentType("application/json")).andExpect(status().isBadRequest());
	}
}
