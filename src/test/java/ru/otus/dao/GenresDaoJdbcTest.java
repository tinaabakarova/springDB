package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import({GenresDaoJdbc.class})
class GenresDaoJdbcTest {
    @Autowired
    GenresDaoJdbc  genresDaoJdbc;

    @Test
    void insert() {
        Genre genre = new Genre(2, "fairytail");
        genresDaoJdbc.insert("fairytail");
        assertThat(genre.equals(genresDaoJdbc.getById(2)));
    }

    @Test
    void getById() {
        Genre genre = new Genre(1, "poem");
        assertThat(genre.equals(genresDaoJdbc.getById(1)));
    }

    @Test
    void getIdByName() {
        Genre genre = new Genre(1, "poem");
        assertThat(genre.equals(genresDaoJdbc.getIdByName("poem")));
    }
}