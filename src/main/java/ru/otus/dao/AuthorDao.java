package ru.otus.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;

public interface AuthorDao extends ReactiveMongoRepository<Author, String> {
    Mono<Author> findByName(String name);
}
