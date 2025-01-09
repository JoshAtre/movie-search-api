package com.atre.movies.api;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

public class OmdbClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OmdbClient omdbClient = new OmdbClient();


}
