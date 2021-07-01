package ru.otus.dao;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DisplayName("Репозиторий на основе Mongo для работы с комментариями ")
class CommentsDaoMongoTest extends AbstractRepositoryTest{

    @Autowired
    private MongoTemplate blockingMongoTemplate;

    @Autowired
    private CommentsDao commentsDao;

    @DisplayName(" должен загружать информацию о комментариях по книге")
    @Test
    void findAllByBookId() throws EntityNotFoundException {
        Optional<Book> book = blockingMongoTemplate.query(Book.class)
                .matching(query(where("name").is("Ruslan and Ludmila"))).one();
        String bookId = book.orElseThrow(EntityNotFoundException::new).getId();

        List<Comment> actual = blockingMongoTemplate.query(Comment.class)
                .matching(query(where("bookId").is(new ObjectId(bookId)))).all();

        Flux<Comment> comments = commentsDao.saveAll(actual);

        StepVerifier
                .create(comments)
                .expectNextCount(actual.size())
                .expectComplete()
                .verify();

        Flux<Comment> expected = commentsDao.findAllByBookId(bookId);

        StepVerifier
                .create(expected)
                .recordWith(ArrayList::new)
                .expectNextCount(actual.size())
                .consumeRecordedWith(c -> assertThat(c).containsExactlyInAnyOrderElementsOf(actual))
                .expectComplete()
                .verify();
    }


}