package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.domain.Genre;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе Mongo для работы с жанрами ")
class GenreDaoMongoTest extends AbstractRepositoryTest{

    @Autowired
    private GenreDao genreDao;

    @DisplayName(" должен загружать информацию о нужном жанре по его имени")
    @Test
    void findByName() {
        Mono<Genre> genre = genreDao.save(new Genre("Test"));

        StepVerifier
                .create(genre)
                .assertNext(genre1 -> assertEquals("Test", genre1.getName()))
                .expectComplete()
                .verify();

        StepVerifier
                .create(genreDao.findByName("Test"))
                .assertNext(genre1 -> assertEquals("Test", genre1.getName()))
                .expectComplete()
                .verify();
    }


}
