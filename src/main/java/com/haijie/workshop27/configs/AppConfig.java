package com.haijie.workshop27.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import static com.haijie.workshop27.Constants.*;

@Configuration
public class AppConfig {
    
    // Inject the mongo connection String
    @Value("${spring.data.mongodb.uri}")
    private String mongoUrl;

    @Bean
    public MongoTemplate createTemplate() {
        MongoClient client = MongoClients.create(mongoUrl);
        return new MongoTemplate(client, DATABASE_BOARDGAMES);
    }
}
