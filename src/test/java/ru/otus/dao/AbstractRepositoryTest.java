package ru.otus.dao;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;

@EnableMongock
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.config", "ru.otus.dao"})
public abstract class AbstractRepositoryTest {
}
