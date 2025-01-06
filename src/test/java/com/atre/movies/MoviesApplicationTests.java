package com.atre.movies;

import com.atre.movies.api.Movies;
import com.atre.movies.model.Movie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MoviesApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(MoviesApplicationTests.class);

	@Test
	void testMovieSearch() {
		String threadName = Thread.currentThread().getName();
		System.out.println("Thread in test: " + threadName);
		String movieTitle = "jab we met";

		int maxResults = 32;
		long start = System.nanoTime();
		List<Movie> movies = Movies.search(movieTitle, maxResults);
		long elapsed = (long) ((System.nanoTime() - start) / 1E6);
		System.out.println("Elapsed time = " + elapsed + " ms");
		System.out.println(movies);
		System.out.println("Number of movies: " + movies.size());
	}
}
