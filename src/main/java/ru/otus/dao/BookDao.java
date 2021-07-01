package ru.otus.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;

public interface BookDao extends ReactiveMongoRepository<Book, String> {
    public Mono<Book> findByName(String name);
}
