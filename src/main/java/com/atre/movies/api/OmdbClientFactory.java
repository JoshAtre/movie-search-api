package com.atre.movies.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class OmdbClientFactory {

    @Autowired
    private ApplicationContext context;

    public OmdbClient getOmdbClient() {
        return context.getBean(OmdbClient.class);
    }

}
