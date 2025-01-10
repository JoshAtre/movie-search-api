package com.atre.movies;

import com.atre.movies.api.Movies;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Test
    public void testSearch(@Autowired MockMvc mockMvc) throws Exception {
//        String expectedJson = "[{\"title\":\"The Disciple\",\"year\":\"2020\",\"releaseDate\":\"30 Apr 2021\"," +
//                "\"genre\":\"Drama, Music\",\"actors\":\"Aditya Modak, Arun Dravid, Sumitra Bhave\",\"plot\":" +
//                "\"Self-doubt, sacrifice and struggle converge into an existential crisis for a devoted classical " +
//                "vocalist as the mastery he strives for remains elusive.\",\"posterUrl\":\"https://m.media-amazon." +
//                "com/images/M/MV5BOWMxMjQ4YTAtNWI1OS00MTIzLWJjNTItNGY1ZmNhYzlkMTdkXkEyXkFqcGc@._V1_SX300.jpg\"," +
//                "\"imdbId\":\"tt11423784\",\"rtRating\":\"96%\"},{\"title\":\"The Devil's Disciple\",\"year\":" +
//                "\"1959\",\"releaseDate\":\"20 Aug 1959\",\"genre\":\"Comedy, History, Romance\",\"actors\":" +
//                "\"Burt Lancaster, Kirk Douglas, Laurence Olivier\",\"plot\":\"The black sheep of a family and the " +
//                "local minister discover their true vocations during the Revolutionary War.\",\"posterUrl\":" +
//                "\"https://m.media-amazon.com/images/M/MV5BYzM4MDlkZDctZmZlMy00ZjUxLTk3MTctMGYzOWY0ZTQ3YmY1XkEyXkF" +
//                "qcGc@._V1_SX300.jpg\",\"imdbId\":\"tt0052735\",\"rtRating\":\"N/A\"}]";

        int maxResults = 2;
        MvcResult result = mockMvc.perform(get("/search?movieTitle=disciple&maxResults=" + maxResults))
                .andExpect(status().isOk())
                .andReturn();
//                .andExpect(content().json(expectedJson));
//                .andExpect(jsonPath("$[0].title").value("The Disciple"))
//                .andExpect(jsonPath("$[1].title").value("The Devil's Disciple"));

        String responseBody = result.getResponse().getContentAsString();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Object>>(){}.getType();
        List<Object> list = gson.fromJson(responseBody, listType);
        Assertions.assertEquals(maxResults, list.size());
    }
}