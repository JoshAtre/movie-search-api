package com.atre.movies.api;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@DependsOn("restTemplate")
class OmdbClient {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://www.omdbapi.com/";
    @Value("${app.omdb.apikey}")
    private String API_KEY = "";

    private static final Logger logger = LoggerFactory.getLogger(OmdbClient.class);


    @Autowired
    public OmdbClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     *
     * @param movieTitle
     * @param maxResults
     * @return
     */
    public List<String> search(String movieTitle, int maxResults) {
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

    public String getByImdbId(String imdbId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format("%s?i=%s&apikey=%s", BASE_URL, imdbId, API_KEY);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

    private List<String> searchByPage(String movieTitle, int page) {
        logger.info("Calling searchByPage, page = {}", page);
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

            List<Object> idsArray = JsonPath.parse(json).read("$.Search..imdbID");
            for (int i = 0; i < idsArray.size(); i++) {
                ids.add(idsArray.get(i).toString());
            }
        } catch (PathNotFoundException ex) {
            return ids;
        }

        return ids;
    }
}