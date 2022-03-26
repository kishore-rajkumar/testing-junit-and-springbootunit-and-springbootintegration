package source;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayMethodsTest {

	private ArrayMethods arrayMethods;

	@BeforeEach
	void setUp() throws Exception {
		// 1. create object of the class
		arrayMethods = new ArrayMethods();
	}

	@AfterEach
	void tearDown() {
		arrayMethods = null;
	}

	@Test
	void testFindIndex() {

		// 1. create object of the class
//		ArrayMethods arrayMethods = new ArrayMethods();

		// 2. set expectation
		int expected = 2;

		// 3. call method
		int result = arrayMethods.findIndex(new int[] { 2, 5, 3, 6 }, 3);

		// 4. compare expected and result(actual)
		assertEquals(expected, result);

	}

	@Test
	void testFindIndex_Not_Found_Returns_Minus_One() {

		// 1. create object of the class
//		ArrayMethods arrayMethods = new ArrayMethods();

		// 2. set expectation
		int expected = -1;

		// 3. call method
		int result = arrayMethods.findIndex(new int[] { 2, 5, 3, 6 }, 10);

		// 4. compare expected and result(actual)
		assertEquals(expected, result);

	}

	@Test
	void testFindIndex_Empty_Array_Returns_Minus_One() {
		// 1. create object of the class
//		ArrayMethods arrayMethods = new ArrayMethods();

		// 2. set expectation
		int expected = -1;
		// 3. call method
		int result = arrayMethods.findIndex(new int[] {}, 10);
		// 4. compare expected and result(actual)
		assertEquals(expected, result, "The findIndex method finds the index of a given number");
	}
	

}
