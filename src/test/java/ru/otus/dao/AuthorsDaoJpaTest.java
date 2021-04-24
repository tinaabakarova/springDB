package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import({AuthorsDaoJpa.class})
class AuthorsDaoJpaTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorsDaoJpa authorsDaoJpa;

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Test
    void getById() {
        Author author = em.find(Author.class, 2L);
        assertThat(authorsDaoJpa.getById(2)).isPresent().get().isEqualTo(author);
    }

    @DisplayName(" должен корректно сохранять всю информацию об авторе")
    @Test
    void save() {
        Author author = new Author(3, "test testovich");
        authorsDaoJpa.save(author);
        assertThat(author).isEqualTo(em.find(Author.class, 3L));
    }

    @DisplayName(" должен загружать информацию о нужном авторе по его имени")
    @Test
    void findByName() {
        Author author = em.find(Author.class, 2L);
        assertThat(author).isEqualTo(authorsDaoJpa.findByName("unit testovich"));
    }


    @DisplayName(" должен удалять заданного автора по его id")
    @Test
    void deleteById() {
        Author author = em.find(Author.class, 2L);
        assertThat(author).isNotNull();
        em.detach(author);
        authorsDaoJpa.deleteById(2);
        Author deleted = em.find(Author.class,2L);
        assertThat(deleted).isNull();
    }

    @DisplayName("должен загружать список всех авторов с полной информацией о них")
    @Test
    void getAll() {
        Author author1 = em.find(Author.class, 1L);
        Author author2 = em.find(Author.class, 2L);
        List<Author> authors = authorsDaoJpa.getAll();
        assertThat(authors).containsAll(List.of(author1, author2));
    }
}
