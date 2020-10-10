package ru.otus.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BooksDaoJdbc implements BooksDao{
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BooksDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public void insert(Book book) {
        Map<String, Object> params = Map.of("id", book.getId(), "name", book.getName(),
                "author", book.getAuthor(), "genre", book.getGenre());
        namedParameterJdbcOperations.update("insert into books (id, name, author, genre) values (:id, :name, :author, :genre)", params);
    }

    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from books where id = :id", params, new BookMapper()
        );
    }

    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select id, name, author, genre from books", new BookMapper());
    }

    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    public void update(Book book){
        Map<String, Object> params = Map.of("id", book.getId(),
                                            "name", book.getName(),
                                            "author", book.getAuthor(),
                                            "genre", book.getGenre());
        namedParameterJdbcOperations.update(
                "update books set name = :name, author = :author, genre = :genre where id = :id", params);

    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            String genre = resultSet.getString("genre");
            return new Book(id, name, author, genre);
        }
    }
}
