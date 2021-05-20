package ru.otus.dao;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DisplayName("Репозиторий на основе Mongo для работы с жанрами ")
class GenreDaoMongoTest extends AbstractRepositoryTest{

    @Autowired
    GenreDao genreDao;
    @Autowired
    MongoTemplate mongoTemplate;

    @DisplayName(" должен загружать информацию о нужном жанре по его имени")
    @Test
    void findByName() {
        Genre genre = mongoTemplate.query(Genre.class)
                .matching(query(where("name").is("poem"))).oneValue();
        assertThat(genre).isEqualTo(genreDao.findByName("poem"));
    }

    @DisplayName(" должен возвращать true если на жанр ссылаются книги")
    @Test
    void isReferenceExistInRepository() {
        Genre genre = mongoTemplate.query(Genre.class)
                .matching(query(where("name").is("poem"))).oneValue();
        List<Book> query = mongoTemplate.query(Book.class)
                .matching(query(where("genre").is(new DBRef("genre", new ObjectId(Objects.requireNonNull(genre).getId())))))
                .all();
        assertEquals(genreDao.isReferenceExistInRepository(genre.getId()), !query.isEmpty());
    }
}
