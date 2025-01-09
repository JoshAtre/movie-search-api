package com.atre.movies.api;

import org.springframework.stereotype.Component;

@Component
public class OmdbClientFactory {

    private final OmdbClient omdbClient;

    public OmdbClientFactory(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    public OmdbClient getOmdbClient() {
        return omdbClient;
    }

}
