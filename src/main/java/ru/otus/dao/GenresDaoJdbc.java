package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenresDaoJdbc implements GenresDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenresDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insert(String name) {
        namedParameterJdbcOperations.update("insert into genres (genre_name) values (:name)",
                Collections.singletonMap("name", name));
    }

    @Override
    public Genre getById(long id) {
        return namedParameterJdbcOperations.queryForObject(
                "select genre_id, genre_name from genres where genre_id = :id", Collections.singletonMap("id", id),
                new GenresMapper());
    }

    @Override
    public Genre getIdByName(String name) {
        return namedParameterJdbcOperations.queryForObject(
                "select genre_id, genre_name from genres where genre_name = :name", Collections.singletonMap("name", name),
                new GenresMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select genre_id, genre_name from genres", new GenresMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from genres where genre_id = :id", params);
    }

    private static class GenresMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("genre_id");
            String name = resultSet.getString("genre_name");
            return new Genre(id, name);
        }
    }
}
