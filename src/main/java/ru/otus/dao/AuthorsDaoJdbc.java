package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorsDaoJdbc implements AuthorsDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorsDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insert(String name) {
        namedParameterJdbcOperations.update("insert into authors (author_name) values (:name)",
                Collections.singletonMap("name", name));
    }

    @Override
    public Author getById(long id) {
        return namedParameterJdbcOperations.queryForObject(
                "select author_id, author_name from authors where author_id = :id", Collections.singletonMap("id", id),
                new AuthorMapper());
    }

    @Override
    public Author getIdByName(String name) {
        return namedParameterJdbcOperations.queryForObject(
                "select  author_id, author_name from authors where author_name = :name", Collections.singletonMap("name", name),
                new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select author_id, author_name from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from authors where author_id = :id", params);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("author_id");
            String name = resultSet.getString("author_name");
            return new Author(id, name);
        }
    }
}
