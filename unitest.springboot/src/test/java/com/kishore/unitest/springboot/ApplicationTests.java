package com.kishore.unitest.springboot;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationTests {

	@Mock
	Filter mockFilter;

	// 1. create object to test
	@InjectMocks
	MovieRecommenderImpl impl;

	@Test
	void testMovieRecommendations() {
		// mock data
		when(mockFilter.getRecommendations("Finding Dory"))
				.thenReturn(new String[] { "Finding Nemo", "Ice Age", "Toy Story" });

		// 2. set expectations
		String[] expected = new String[] { "Finding Nemo", "Ice Age", "Toy Story" };

		// 3. call method to test and get result(actual)
		String[] result = impl.recommendMovies("Finding Dory");

		// 4. compare
		assertArrayEquals(expected, result);
	}

	@Test
	void testMovieRecommendations_noRecommentationsFound() {
		// mock data
		when(mockFilter.getRecommendations("Spiderman")).thenReturn(new String[] {});

		// 2. set expectations
		String[] expected = new String[] {};

		// 3. call method to test and get result(actual)
		String[] result = impl.recommendMovies("Spiderman");

		// 4. compare
		assertArrayEquals(expected, result);

	}

	@Test
	void testMovieRecommendations_nullInputMovieNameParameter_IllegalArgumentException() {
		// mock data
//		when(mockFilter.getRecommendations("Spiderman")).thenReturn(new String[] {});

		// 2. set expectations
//		String[] expected = new String[] {};

		// 3. call method to test and get result(actual)
//		String[] result = impl.recommendMovies(null);

		// 4. compare
		assertThrows(IllegalArgumentException.class, () -> impl.recommendMovies(null));
	}

}
