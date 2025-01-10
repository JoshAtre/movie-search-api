package com.atre.movies.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OmdbClientTest {

    @MockitoBean
    private RestTemplate restTemplateMock;

    @InjectMocks
    @Autowired
    private OmdbClient omdbClient;

    @Test
    void testSearchValid() {
        String movieTitle = "mission";
        int maxResults = 2;
        String responseBody = "{\"Search\":[{\"Title\":\"Mission: Impossible - Ghost Protocol\",\"Year\":\"2011\"," +
                "\"imdbID\":\"tt1229238\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BM" +
                "TY4MTUxMjQ5OV5BMl5BanBnXkFtZTcwNTUyMzg5Ng@@._V1_SX300.jpg\"},{\"Title\":\"Mission: Impossible\"," +
                "\"Year\":\"1996\",\"imdbID\":\"tt0117060\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon." +
                "com/images/M/MV5BOGZjNDlkMTYtMTJkZi00OTkzLWI4NDEtYTA2ODQyMjcwYTdlXkEyXkFqcGc@._V1_SX300.jpg\"}," +
                "{\"Title\":\"Mission: Impossible - Rogue Nation\",\"Year\":\"2015\",\"imdbID\":\"tt2381249\"," +
                "\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZjUwZjg2ZjAtY2RhZi00YmZjL" +
                "TlhNGQtOWQwNDk1MjhhM2NhXkEyXkFqcGc@._V1_SX300.jpg\"},{\"Title\":\"Mission: Impossible III\"," +
                "\"Year\":\"2006\",\"imdbID\":\"tt0317919\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon." +
                "com/images/M/MV5BNzY1MzdjMjYtNDJiZS00N2U4LWI0MWQtZTRiZWYxMzU3ZmI4XkEyXkFqcGc@._V1_SX300.jpg\"}," +
                "{\"Title\":\"Mission: Impossible - Fallout\",\"Year\":\"2018\",\"imdbID\":\"tt4912910\",\"Type\":" +
                "\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTk3NDY5MTU0NV5BMl5BanBnXkFtZTgwNDI" +
                "3MDE1NTM@._V1_SX300.jpg\"},{\"Title\":\"Mission: Impossible II\",\"Year\":\"2000\",\"imdbID\":" +
                "\"tt0120755\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYWFjM2NhMT" +
                "AtNDU1My00ODUxLTkwMzYtODQzNzM0ODM0ZWQ0XkEyXkFqcGc@._V1_SX300.jpg\"},{\"Title\":\"Mission: Impossible" +
                " - Dead Reckoning Part One\",\"Year\":\"2023\",\"imdbID\":\"tt9603212\",\"Type\":\"movie\"," +
                "\"Poster\":\"https://m.media-amazon.com/images/M/MV5BN2U4OTdmM2QtZTkxYy00ZmQyLTg2N2UtMDdmMGJmNDhlZDU" +
                "1XkEyXkFqcGc@._V1_SX300.jpg\"},{\"Title\":\"Mission to Mars\",\"Year\":\"2000\",\"imdbID\":" +
                "\"tt0183523\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNGI4Y2ZiZTUt" +
                "YjQzNy00YzJmLWE5NjYtYjdmNWE4ZTlkNDA0XkEyXkFqcGc@._V1_SX300.jpg\"},{\"Title\":\"The Mission\",\"Year" +
                "\":\"1986\",\"imdbID\":\"tt0091530\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/" +
                "images/M/MV5BODcxMDJiMzQtNzVkYS00ZDI3LWEzYmEtY2NiYTIzZWQzYzRlXkEyXkFqcGc@._V1_SX300.jpg\"}," +
                "{\"Title\":\"Asterix & Obelix: Mission Cleopatra\",\"Year\":\"2002\",\"imdbID\":\"tt0250223\"," +
                "\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMGYxYzkwZGUtNzkzNS00YTE2L" +
                "WE2MjUtN2Q4YWY1ZjQ4ZDFjXkEyXkFqcGc@._V1_SX300.jpg\"}],\"totalResults\":\"1683\",\"Response\":" +
                "\"True\"}";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        Mockito.when(restTemplateMock.exchange(any(String.class), eq(HttpMethod.GET), any(), any(Class.class)))
                .thenReturn(responseEntity);

        List<String> ids = omdbClient.search(movieTitle, maxResults);
        Assertions.assertEquals(maxResults, ids.size());
        Assertions.assertEquals("tt1229238", ids.get(0));
        Assertions.assertEquals("tt0117060", ids.get(1));
    }

    @Test
    void testGetByImdbId() {
        String responseBody = "{\"Title\":\"Kenichi: The Mightiest Disciple\",\"Year\":\"2006–2014\",\"Rated\":\"TV-14\"," +
                "\"Released\":\"07 Oct 2006\",\"Runtime\":\"25 min\",\"Genre\":\"Animation, Action, Comedy\"," +
                "\"Director\":\"N/A\",\"Writer\":\"Shun Matsuena\",\"Actors\":\"Jessie James Grelle, Carrie Savage, " +
                "Christopher Sabat\",\"Plot\":\"\\\"Weak Legs\\\" Kenichi Shirahama would rather spend his time " +
                "reading self improvement books than fighting.\",\"Language\":\"English, Japanese\",\"Country\":" +
                "\"Japan\",\"Awards\":\"N/A\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNTdjOTlkNDQtNGI0" +
                "Mi00NzZlLTgwZTktYzJmOTU0MDcwYzg3XkEyXkFqcGc@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet " +
                "Movie Database\",\"Value\":\"8.1/10\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"8.1\",\"imdbVotes\":" +
                "\"2,481\",\"imdbID\":\"tt1424037\",\"Type\":\"series\",\"totalSeasons\":\"3\",\"Response\":\"True\"}";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        Mockito.when(restTemplateMock.exchange(any(String.class), eq(HttpMethod.GET), any(), any(Class.class)))
                .thenReturn(responseEntity);
        String movieInfo = omdbClient.getByImdbId("dummy");

        Assertions.assertEquals(responseBody, movieInfo);
    }
}
