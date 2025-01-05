package com.atre.movies;

import com.atre.movies.model.Movie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class MoviesApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(MoviesApplicationTests.class);

	@Test
	void testMovieSearch() {
		String threadName = Thread.currentThread().getName();
		System.out.println("Thread in test: " + threadName);
		String movieTitle = "mission impossible";

		int maxResults = 24;
		long start = System.nanoTime();
		List<Movie> movies = Movies.search(movieTitle, maxResults);
//		List<String> ids = OmdbClient.search(movieTitle, maxResults);
		long elapsed = (long) ((System.nanoTime() - start) / 1E6);
		System.out.println("Elapsed time = " + elapsed + " ms");
		System.out.println(movies);
		System.out.println("Number of movies: " + movies.size());
	}

	@Test
	void testOmdbSearch() {
		String movieTitle = "disciple";
//		OmdbClient.search(movieTitle);
	}

//	@Test
//	void testGetIds() throws JSONException {
//		Movies.getMovieIds("disciple");
//	}
//	@Test
//	void testOmdbSearch() throws JSONException {
//		List<Movie> movies = Movies.search("disciple");
//		Collections.sort(movies);
//		movies.forEach(System.out::println);
//	}
}
