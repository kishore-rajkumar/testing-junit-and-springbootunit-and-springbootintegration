package com.kishore.unitest.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieRecommenderImpl implements MovieRecommender {

	@Autowired
	private Filter filter;

	@Override
	public String[] recommendMovies(String movie) {

		if (movie == null) {
			throw new IllegalArgumentException("movie parameter cannot be null");
		}

		// print the name of interface implementation being used
		System.out.println("\nName of the filter in use: " + filter + "\n");

		String[] movies = filter.getRecommendations(movie);

		return movies;
	}

}
