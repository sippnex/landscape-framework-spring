package com.sippnex.landscape.core;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConfigurationProperties(prefix = "landscape")
@EnableMongoRepositories(basePackages = {"com.sippnex.landscape.core.security.repository", "com.sippnex.landscape.core.app.repository"})
@ComponentScan(basePackages = {"com.sippnex.landscape.core", "com.sippnex.landscape.core.security.service", "com.sippnex.landscape.core.security.config"})
public class LandscapeConfig {

    private String root;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
