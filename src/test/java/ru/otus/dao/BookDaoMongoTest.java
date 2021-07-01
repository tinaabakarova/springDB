package ru.otus.dao;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.events.MongoBookCascadeDeleteEventsListener;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DisplayName("Репозиторий на основе Mongo для работы с книгами ")
@Import(MongoBookCascadeDeleteEventsListener.class)
class BookDaoMongoTest extends AbstractRepositoryTest{

    @Autowired
    private MongoTemplate blockingMongoTemplate;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CommentsDao commentsDao;

    @DisplayName(" должен загружать информацию о нужной книге по ее имени")
    @Test
    void findByName() {
        Book book = blockingMongoTemplate.query(Book.class)
                .matching(query(where("name").is("Ruslan and Ludmila"))).oneValue();

        Mono<Book> bookMono = bookDao.save(Objects.requireNonNull(book));

        StepVerifier
                .create(bookMono)
                .assertNext(book1 -> assertEquals("Ruslan and Ludmila", book1.getName()))
                .expectComplete()
                .verify();

        StepVerifier
                .create(bookDao.findByName("Ruslan and Ludmila"))
                .assertNext(book1 -> assertEquals("Ruslan and Ludmila", book1.getName()))
                .expectComplete()
                .verify();
    }

    @DisplayName(" должен удалять книгу и все связанные с ней комментарии")
    @Test
    void deleteBook() {
        Book book = blockingMongoTemplate.query(Book.class)
                .matching(query(where("name").is("Ruslan and Ludmila"))).oneValue();

        List<Comment> query = blockingMongoTemplate.query(Comment.class)
                .matching(query(where("book.$id").is(new ObjectId(Objects.requireNonNull(book).getId()))))
                .all();

        Mono<Book> bookMono = bookDao.save(Objects.requireNonNull(book));
        Flux<Comment> commentFlux = commentsDao.saveAll(query);

        StepVerifier
                .create(commentFlux)
                .expectNextCount(query.size())
                .expectComplete()
                .verify();

        StepVerifier
                .create(bookMono)
                .assertNext(book1 -> assertEquals("Ruslan and Ludmila", book1.getName()))
                .expectComplete()
                .verify();

        StepVerifier
                .create(bookDao.deleteById(book.getId()))
                .verifyComplete();

        StepVerifier
                .create(commentsDao.findAllByBookId(book.getId()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}
