package com.chilleric.franchise_sys.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfiguration {

  @Bean("mongo_system")
  @ConfigurationProperties("mongodb.system")
  public MongoProperties getSystemDBProperties() {
    return new MongoProperties();
  }

  @Bean("mongo_system_template")
  public MongoTemplate getSystemDBTemplate(
    @Qualifier("mongo_system") MongoProperties mongo
  ) {
    SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(
      mongo.getUri()
    );
    return new MongoTemplate(factory);
  }

  @Bean("mongo_crm")
  @ConfigurationProperties("mongodb.crm")
  public MongoProperties getCrmDBProperties() {
    return new MongoProperties();
  }

  @Bean("mongo_crm_template")
  public MongoTemplate getCrmDBTemplate(@Qualifier("mongo_crm") MongoProperties mongo) {
    SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(
      mongo.getUri()
    );
    return new MongoTemplate(factory);
  }

  @Bean("mongo_information")
  @ConfigurationProperties("mongodb.information")
  public MongoProperties getInformationDBProperties() {
    return new MongoProperties();
  }

  @Bean("mongo_information_template")
  public MongoTemplate getInformationDBTemplate(
    @Qualifier("mongo_information") MongoProperties mongo
  ) {
    SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(
      mongo.getUri()
    );
    return new MongoTemplate(factory);
  }
}
