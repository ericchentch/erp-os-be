package com.chilleric.franchise_sys.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfiguration {

    @Bean("mongo_database")
    @ConfigurationProperties("spring.data.mongodb")
    public MongoProperties getAuthentication() {
        return new MongoProperties();
    }

    @Bean("mongo_secondary_database")
    @ConfigurationProperties("mongodb.secondary")
    public MongoProperties getSecondaryAuthentication() {
        return new MongoProperties();
    }

    @Bean("mongo_template")
    public MongoTemplate getAuthenticationMongoTemplate() {
        return new MongoTemplate(authenticationMongoFactory(getAuthentication()));
    }

    @Bean
    public MongoDatabaseFactory authenticationMongoFactory(
            @Qualifier("mongo_database") MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }

    @Bean("mongo_secondary_template")
    public MongoTemplate getSecondaryTemplate(
            @Qualifier("mongo_secondary_database") MongoProperties mongo) {
        SimpleMongoClientDatabaseFactory clientData =
                new SimpleMongoClientDatabaseFactory(mongo.getUri());
        return new MongoTemplate(clientData);
    }
}
