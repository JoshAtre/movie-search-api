package com.atre.movies.api;

import com.atre.movies.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MoviesTest {
	private static final String VALID_MOVIE_TITLE = "Inception";
	private static final String EMPTY_MOVIE_TITLE = "";
	private static final int VALID_MAX_RESULTS = 5;
	private static final int INVALID_MAX_RESULTS = 200;

	@MockitoBean
	private OmdbClientFactory omdbClientFactoryMock;

	@Mock
	private OmdbClient omdbClientMock;

	@InjectMocks
	@Autowired
	private Movies movies;

	@Test
	void testSearchWithNullMovieTitle() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			movies.search(null, VALID_MAX_RESULTS);
		});
		assertEquals("movieTitle must not be null or empty", e.getMessage());
	}

	@Test
	void testSearchWithEmptyMovieTitle() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			movies.search(EMPTY_MOVIE_TITLE, VALID_MAX_RESULTS);
		});
		assertEquals("movieTitle must not be null or empty", e.getMessage());
	}

	@Test
	void testSearchWithInvalidMaxResults() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			movies.search(VALID_MOVIE_TITLE, INVALID_MAX_RESULTS);
		});
		assertEquals("maxResults must be between 1 and 100", exception.getMessage());
	}

	@Test
	void testSearchWithZeroMaxResults() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			movies.search(VALID_MOVIE_TITLE, 0);
		});
		assertEquals("maxResults must be between 1 and 100", exception.getMessage());
	}

	@Test
	void testSearchValidSmallMaxResults() {
		String movieTitle = "mission";
		int maxResults = 5;

		Mockito.when(omdbClientFactoryMock.getOmdbClient()).thenReturn(omdbClientMock);

		// Mock OmdbClient to return 5 movie IDs
		List<String> mockIds = Arrays.asList("tt001", "tt002", "tt003", "tt004", "tt005");
		Mockito.when(omdbClientMock.search(movieTitle, maxResults)).thenReturn(mockIds);

		Mockito.when(omdbClientMock.getByImdbId(any(String.class))).thenReturn("{\"Title\": \"Mission: Impossible\", " +
						"\"Year\": \"1996\", \"Rated\": \"PG-13\", \"Released\": \"22 May 1996\", \"Runtime\": " +
						"\"110 min\", \"Genre\": \"Action, Adventure, Thriller\", \"Director\": \"Brian De Palma\", " +
						"\"Writer\": \"Bruce Geller, David Koepp, Steven Zaillian\", \"Actors\": \"Tom Cruise, " +
						"Jon Voight, Emmanuelle Béart\", \"Plot\": \"An American agent, under false suspicion of " +
						"disloyalty, must discover and expose the real spy without the help of his organization.\", " +
						"\"Language\": \"English, French, Czech\", \"Country\": \"United States\", \"Awards\": " +
						"\"3 wins & 17 nominations total\", \"Poster\": \"https://m.media-amazon.com/images/M/MV5BOGZ" +
						"jNDlkMTYtMTJkZi00OTkzLWI4NDEtYTA2ODQyMjcwYTdlXkEyXkFqcGc@._V1_SX300.jpg\", " +
						"\"Ratings\": [{\"Source\": \"Internet Movie Database\", \"Value\": \"7.2/10\"}," +
						"{\"Source\": \"Rotten Tomatoes\", \"Value\": \"65%\"}, {\"Source\": \"Metacritic\", " +
						"\"Value\": \"59/100\"}], \"Metascore\": \"59\", \"imdbRating\": \"7.2\", " +
						"\"imdbVotes\": \"481,517\", \"imdbID\": \"tt0117060\", \"Type\": \"movie\", " +
						"\"DVD\": \"N/A\", \"BoxOffice\": \"$180,981,856\", \"Production\": \"N/A\", \"Website\": " +
						"\"N/A\", \"Response\": \"True\"}\n");

		List<Movie> movieList = movies.search(movieTitle, maxResults);

		assertNotNull(movieList, "The result list should not be null.");
		assertFalse(movieList.isEmpty(), "The result list should not be empty.");
		assertEquals(maxResults, movieList.size(), "The result list should contain exactly maxResults movies.");
	}

}
