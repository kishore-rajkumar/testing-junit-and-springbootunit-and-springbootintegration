package source.mocking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
	
	@Mock
	private StudentDao dao;
	
	@InjectMocks
	private StudentService service;
	
	/*
	 * This class has been enhanced with Mockito annotations
	 * 
	 * */

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindTotal() {

		// mock dao object | enhanced with @Mock
//		StudentDao dao = mock(StudentDao.class);

		// 1. Create object | enhanced with @InjectMocks
//		StudentService service = new StudentService(dao);

		// mock dao calls
		when(dao.getMarks()).thenReturn(new int[] { 15, 2, 4 });

		// 2. expected
		int expected = 21;

		// 3. call method
		int result = service.findTotal();

		// 4. compare
		assertEquals(expected, result);

	}

	@Test
	void testFindTotal_With_Empty_Array_Returns_Zero() {

		// mock dao object | enhanced with @Mock
//		StudentDao dao = mock(StudentDao.class);

		// 1. Create object to test | enhanced with @InjectMocks
//		StudentService service = new StudentService(dao);

		// mock dao call for data
		when(dao.getMarks()).thenReturn(new int[] {});

		// 2. expected
		int expected = 0;

		// 3. call method @ test
		int result = service.findTotal();

		// 4. compare result
		assertEquals(expected, result);

	}

}
