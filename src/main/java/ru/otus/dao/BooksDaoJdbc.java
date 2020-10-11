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
    private GenresDao genresDao;
    private AuthorsDao authorsDao;

    public BooksDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations, AuthorsDao authorsDao,
                        GenresDao genresDao)
    {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorsDao = authorsDao;
        this.genresDao = genresDao;
    }

    @Override
    public void insert(String name, String author, String genre) {
        long authorId = authorsDao.getIdByName(author).getId();
        long genreId = genresDao.getIdByName(genre).getId();
        Map<String, Object> params = Map.of("name", name,
                "author", authorId, "genre", genreId);

        namedParameterJdbcOperations.update("insert into books (name, author, genre) values (:name, :author, :genre)", params);
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name, author_name, genre_name from books, authors, genres where id = :id and " +
                        "books.author = authors.author_id and books.genre = genres.genre_id", params, new BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select id, name, author_name, genre_name from books, authors, genres " +
                "where books.author = authors.author_id and books.genre = genres.genre_id", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    @Override
    public void update(Book book){
        long authorId = authorsDao.getIdByName(book.getAuthor()).getId();
        long genreId = genresDao.getIdByName(book.getGenre()).getId();
        Map<String, Object> params = Map.of("id", book.getId(),
                                            "name", book.getName(),
                                            "author", authorId,
                                            "genre", genreId);
        namedParameterJdbcOperations.update(
                "update books set name = :name, author = :author, genre = :genre where id = :id", params);

    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author_name");
            String genre = resultSet.getString("genre_name");
            return new Book(id, name, author, genre);
        }
    }
}
