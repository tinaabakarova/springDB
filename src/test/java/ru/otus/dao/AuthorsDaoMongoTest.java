package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.domain.Author;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе Mongo для работы с авторами ")
class AuthorsDaoMongoTest extends AbstractRepositoryTest{

    @Autowired
    private AuthorDao authorsDao;

    @DisplayName(" должен загружать информацию о нужном авторе по его имени")
    @Test
    void findByName() {
        Mono<Author> author = authorsDao.save(new Author("Test"));

        StepVerifier
                .create(author)
                .assertNext(author1 -> assertEquals("Test", author1.getName()))
                .expectComplete()
                .verify();

        StepVerifier
                .create(authorsDao.findByName("Test"))
                .assertNext(author1 -> assertEquals("Test", author1.getName()))
                .expectComplete()
                .verify();
    }
}
