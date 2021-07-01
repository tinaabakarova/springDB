package ru.otus.config;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class ResourceConfig {

    final MongoClient mongoClientReactive;
    final com.mongodb.client.MongoClient mongoClientBlocking;

    public ResourceConfig(MongoClient mongoClientReactive, com.mongodb.client.MongoClient mongoClientBlocking) {
        this.mongoClientReactive = mongoClientReactive;
        this.mongoClientBlocking = mongoClientBlocking;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClientReactive, "library");
    }

    @Bean
    public MongoTemplate blockingMongoTemplate() {
        return new MongoTemplate(mongoClientBlocking, "library");
    }

}
