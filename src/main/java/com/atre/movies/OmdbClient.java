package com.atre.movies;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OmdbClient {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://www.omdbapi.com/";
    private static final String API_KEY = "?";

    public static List<String> search(String movieTitle, int maxResults) {
        // Return IMDB ids of movies matching movieTitle
        List<String> allIds = new ArrayList<>();
        int page = 1;
        while (allIds.size() < maxResults) {
            List<String> ids = searchByPage(movieTitle, page);
            if (ids.isEmpty()) {
                break;
            }

            if (allIds.size() + ids.size() <= maxResults) {
                allIds.addAll(ids);
            } else {
                allIds.addAll(ids.subList(0, maxResults - allIds.size()));
            }
            page++;
        }

        return allIds;
    }

    private static List<String> searchByPage(String movieTitle, int page) {
        System.out.println("Calling searchByPage, page = " + page);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format("%s?apikey=%s&s=%s*&page=%d", BASE_URL,
                API_KEY, movieTitle, page);
        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

        List<String> ids = new ArrayList<>();

        try {
            String success = JsonPath.parse(json).read("$.Response");
            if (success.equals("False")) {
                return ids;
            }

            JSONArray idsArray = JsonPath.parse(json).read("$.Search..imdbID");
            for (int i = 0; i < idsArray.size(); i++) {
                ids.add(idsArray.get(i).toString());
            }
        } catch (PathNotFoundException ex) {
            return ids;
        }

        return ids;
    }

    public static String getByImdbId(String imdbId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format("%s?i=%s&apikey=%s", BASE_URL, imdbId, API_KEY);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

//    public static String searchAll(String movieTitle) throws JSONException {
//        List<String> list = MovieIds.getMovieIds(movieTitle);
//        StringBuilder body = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            body.append(search(list.get(i)));
//        }
//        return body.toString();
//    }
}