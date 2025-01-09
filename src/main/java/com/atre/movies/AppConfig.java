package com.atre.movies;

import com.atre.movies.api.OmdbClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);

        return new RestTemplate(factory);
    }

    @Bean
    public OmdbClientFactory omdbClientFactory() {
        return new OmdbClientFactory();
    }
}
