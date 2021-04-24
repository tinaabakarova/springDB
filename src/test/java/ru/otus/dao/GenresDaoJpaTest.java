package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import({GenresDaoJpa.class})
class GenresDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    GenresDaoJpa genresDaoJpa;

    @DisplayName(" должен корректно сохранять всю информацию о жанре")
    @Test
    void save() {
        Genre genre = new Genre(2, "fairytale");
        genresDaoJpa.save(genre);
        assertThat(genre).isEqualTo(em.find(Genre.class, 2L));
    }

    @DisplayName(" должен загружать информацию о нужном жанре по его id")
    @Test
    void getById() {
        Genre genre = new Genre(1, "poem");
        assertThat(genresDaoJpa.getById(1)).isPresent().get().isEqualTo(genre);
    }

    @DisplayName(" должен загружать информацию о нужном жанре по его имени")
    @Test
    void findByName() {
        Genre genre = em.find(Genre.class, 1L);
        assertThat(genre).isEqualTo(genresDaoJpa.findByName("poem"));
    }

    @DisplayName(" должен удалять заданный жанр по его id")
    @Test
    void deleteById() {
        Genre genre = em.find(Genre.class, 2L);
        assertThat(genre).isNotNull();
        em.detach(genre);
        genresDaoJpa.deleteById(2);
        Genre deleted = em.find(Genre.class,2L);
        assertThat(deleted).isNull();
    }

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void getAll() {
        Genre genre1 = em.find(Genre.class, 1L);
        Genre genre2 = em.find(Genre.class, 2L);
        List<Genre> genres = genresDaoJpa.getAll();
        assertThat(genres).containsAll(List.of(genre1, genre2));
    }
}
