package ru.otus.dao;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Author;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DisplayName("Репозиторий на основе Mongo для работы с авторами ")
class AuthorsDaoMongoTest extends AbstractRepositoryTest{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorDao authorsDao;

    @DisplayName(" должен загружать информацию о нужном авторе по его имени")
    @Test
    void findByName() {
        Author author = mongoTemplate.query(Author.class)
                .matching(query(where("name").is("Pushkin A.S"))).oneValue();
        assertThat(author).isEqualTo(authorsDao.findByName("Pushkin A.S"));
    }

    @DisplayName(" должен возвращать true если на автора ссылаются книги")
    @Test
    void isReferenceExistInRepository() {
        Author author = mongoTemplate.query(Author.class)
                .matching(query(where("name").is("Pushkin A.S"))).oneValue();
        List<Book> query = mongoTemplate.query(Book.class)
                .matching(query(where("author").is(new DBRef("author", new ObjectId(Objects.requireNonNull(author).getId())))))
                .all();
        assertEquals(authorsDao.isReferenceExistInRepository(author.getId()), !query.isEmpty());
    }
}
