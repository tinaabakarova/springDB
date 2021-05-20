package ru.otus.dao;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.events.MongoBookCascadeDeleteEventsListener;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DisplayName("Репозиторий на основе Mongo для работы с книгами ")
@Import(MongoBookCascadeDeleteEventsListener.class)
class BookDaoMongoTest extends AbstractRepositoryTest{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CommentsDao commentsDao;

    @DisplayName(" должен загружать информацию о нужной книге по ее имени")
    @Test
    void findByName() {
        Book book = mongoTemplate.query(Book.class)
                .matching(query(where("name").is("Ruslan and Ludmila"))).oneValue();
        assertThat(book).isEqualTo(bookDao.findByName("Ruslan and Ludmila"));
    }

    @DisplayName(" должен удалять книгу и все связанные с ней комментарии")
    @Test
    void deleteBook() {
        Book book = mongoTemplate.query(Book.class)
                .matching(query(where("name").is("Ruslan and Ludmila"))).oneValue();
        List<Comment> query = mongoTemplate.query(Comment.class)
                .matching(query(where("book.$id").is(new ObjectId(Objects.requireNonNull(book).getId()))))
                .all();
        bookDao.deleteById(book.getId());
        Assertions.assertNull(bookDao.findByName("Ruslan and Ludmila"));
        Assertions.assertTrue(commentsDao.findAllByBookId(book.getId()).isEmpty());
    }
}
