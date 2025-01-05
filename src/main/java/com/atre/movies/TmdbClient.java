//package com.atre.movies;
//
//import com.jayway.jsonpath.JsonPath;
//import net.minidev.json.JSONArray;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//public class TmdbClient {
//    private static final RestTemplate restTemplate = new RestTemplate();
//    private static final String BASE_URL = "https://api.themoviedb.org/";
//    private static final String API_KEY = "e6b898b6d15ed8360d6888f120d6f721";
//    private static final String MOVIE_SEARCH_SUBPATH = "3/search/movie";
//
//    public static List<String> search(String movieTitle) {
//        // Return TMDB ids of movies matching movieTitle
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        String url = String.format("%s%s?api_key=%s&query=%s", BASE_URL, MOVIE_SEARCH_SUBPATH,
//                API_KEY, movieTitle);
//        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
//        JSONArray idsArray = JsonPath.parse(json).read("$.results..id");
//
//        List<String> ids = new ArrayList<>();
//        for (int i = 0; i < idsArray.size(); i++) {
//            ids.add(idsArray.get(i).toString());
//        }
//        return ids;
//    }
//
//    public static Optional<String> getImdbId(String id) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        String url = String.format("%s3/movie/%s?api_key=%s", BASE_URL, id, API_KEY);
//        String body = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
//
//        try {
//            JSONObject jsonObject = new JSONObject(body);
//            String imdbId = jsonObject.getString("imdb_id");
//            if (imdbId.equals("null") || imdbId.isEmpty()) {
//                return Optional.empty();
//            }
//            return Optional.of(imdbId);
//        } catch (JSONException ex) {
//            return Optional.empty();
//        }
//    }
//}
