package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import({BooksDaoJpa.class, AuthorsDaoJpa.class, GenresDaoJpa.class})
class BooksDaoTest {
    private final Author author = new Author(1, "Pushkin A.S");
    private final Genre genre = new Genre(1, "poem");
    private final Book ruslanAndLudmila = new Book(1, "Ruslan and Ludmila", author, genre);
    private final Book kingSaltanFairytale = new Book(2, "King Saltan fairytale", author, genre);
    private final Book testBook = new Book( 3,"test", author, genre);

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BooksDaoJpa booksDaoJpa;

    @DisplayName(" должен корректно сохранять всю информацию о книге")
    @Test
    void saveBookTest() {
        booksDaoJpa.save(testBook);
        assertThat(testBook).isEqualTo(em.find(Book.class, testBook.getId()));
    }

    @DisplayName(" должен загружать информацию о нужной книге по его id")
    @Test
    void getByIdTest() {
        Book book = em.find(Book.class, 2L);
        assertThat(booksDaoJpa.getById(2)).isPresent().get().isEqualTo(book);
    }


    @DisplayName(" должен удалять заданную книгу по его id")
    @Test
    void deleteByIdBookTest() {
        Book book = em.find(Book.class, 2L);
        assertThat(book).isNotNull();
        em.detach(book);
        booksDaoJpa.deleteById(2);
        Book deleted = em.find(Book.class,2L);
        assertThat(deleted).isNull();
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void getAllTest() {
        List<Book> books = booksDaoJpa.getAll();
        assertThat(books).containsAll(List.of(ruslanAndLudmila, kingSaltanFairytale));
    }

    @DisplayName(" должен изменять имя заданную книгу по его id")
    @Test
    void updateTest() {
        Book book = em.find(Book.class, 2L);
        assertThat(book).isNotNull();
        String oldName = book.getName();
        em.detach(book);
        Book bookNew = em.find(Book.class, 2L);
        bookNew.setName("changed");
        booksDaoJpa.update(bookNew);
        Book updated = em.find(Book.class,2L);
        assertThat(updated.getName()).isNotEqualTo(oldName).isEqualTo("changed");
    }
}
