package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.dao.AuthorsDaoJdbc;
import ru.otus.dao.BooksDaoJdbc;
import ru.otus.dao.GenresDaoJdbc;
import ru.otus.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import({BooksDaoJdbc.class, AuthorsDaoJdbc.class, GenresDaoJdbc.class})
class BooksDaoTest {

    @Autowired
    private AuthorsDaoJdbc authorsDaoJdbc;
    @Autowired
    private GenresDaoJdbc genresDaoJdbc;
    @Autowired
    private BooksDaoJdbc booksDaoJdbc;

    @Test
    void insertBookTest() {
        Book book = new Book(3, "test", "unit testovich", "poem");
        booksDaoJdbc.insert("test", "unit testovich", "poem");
        assertThat(book.equals(booksDaoJdbc.getById(3)));
    }

    @Test
    void getByIdTest() {
        Book book = new Book(1, "Ruslan and Ludmila", "unit testovich", "poem");
        assertThat(book.equals(booksDaoJdbc.getById(1)));
    }

    @Test
    void deleteByIdBookTest() {
        Book book = new Book(3, "test", "unit testovich", "poem");
        booksDaoJdbc.insert("test", "unit testovich", "poem");
        booksDaoJdbc.deleteById(3);
        assertThrows(EmptyResultDataAccessException.class, () -> {booksDaoJdbc.getById(3);});
    }

    @Test
    void getAllByIdTest() {
        List<Book> books = booksDaoJdbc.getAll();
        assertThat(books.containsAll(List.of(new Book(1, "Ruslan and Ludmila", "unit testovich", "poem"),
                                    new Book(2, "King Saltan fairytail", "unit testovich", "poem"))));
    }

    @Test
    void updateByIdTest() {
        Book book = new Book(1, "test", "unit testovich", "poem");
        booksDaoJdbc.update(book);
        assertThat(booksDaoJdbc.getById(1).equals(book));
    }
}