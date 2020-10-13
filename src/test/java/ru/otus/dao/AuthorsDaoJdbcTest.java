package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import({AuthorsDaoJdbc.class})
class AuthorsDaoJdbcTest {

    @Autowired
    private AuthorsDaoJdbc authorsDaoJdbc;

    @Test
    void getById() {
        Author author = new Author(1, "unit testovich");
        assertThat(author.equals(authorsDaoJdbc.getById(1)));
    }

    @Test
    void insert() {
        Author author = new Author(2, "test testovich");
        authorsDaoJdbc.insert("test");
        assertThat(author.equals(authorsDaoJdbc.getById(2)));
    }

    @Test
    void getIdByName() {
        Author author = new Author(1, "unit testovich");
        assertThat(author.equals(authorsDaoJdbc.getIdByName("unit testovich")));
    }
}