package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
class GenresDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    GenresDao genresDao;

    @DisplayName(" должен загружать информацию о нужном жанре по его имени")
    @Test
    void findByName() {
        Genre genre = em.find(Genre.class, 1L);
        assertThat(genre).isEqualTo(genresDao.findByName("poem"));
    }
}
