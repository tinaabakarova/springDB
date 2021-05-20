package ru.otus.dao;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DisplayName("Репозиторий на основе Mongo для работы с комментариями ")
class CommentsDaoMongoTest extends AbstractRepositoryTest{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentsDao commentsDao;

    @DisplayName(" должен загружать информацию о комментариях по книге")
    @Test
    void findAllByBookId() {
        Book book = mongoTemplate.query(Book.class)
                .matching(query(where("name").is("Ruslan and Ludmila"))).oneValue();
        List<Comment> query = mongoTemplate.query(Comment.class)
                .matching(query(where("book").is(new DBRef("book", new ObjectId(Objects.requireNonNull(book).getId())))))
                .all();
        assertEquals(query, commentsDao.findAllByBookId(book.getId()));
    }


}