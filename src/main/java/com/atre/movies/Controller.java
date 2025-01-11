package com.atre.movies;

import com.atre.movies.api.Movies;
import com.atre.movies.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Movies movies;

    @GetMapping(value = "/hello", produces = "text/plain")
    public String hello() {
        return "hello";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List<Movie>> search(@RequestParam(name = "movieTitle", required = true) String movieTitle,
                              @RequestParam(name = "maxResults", required = true) int maxResults) {

        return new ResponseEntity<>(movies.search(movieTitle, maxResults), HttpStatus.OK);
    }
}
