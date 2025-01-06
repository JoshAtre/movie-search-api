package com.atre.movies;

import com.atre.movies.api.Movies;
import com.atre.movies.model.Movie;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @GetMapping(value = "/hello", produces = "text/plain")
    public String hello() {
        return "hello";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/search", produces = "application/json")
    public List<Movie> search(String movieTitle, int maxResults) {
        return Movies.search(movieTitle, maxResults);
    }
}
