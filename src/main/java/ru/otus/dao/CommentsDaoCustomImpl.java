package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import ru.otus.domain.Comment;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
public class CommentsDaoCustomImpl implements CommentsDaoCustom {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<Comment> findAllByBookId(String id) {
        return reactiveMongoTemplate.query(Comment.class)
                .matching(query(where("book._id").is(new ObjectId(id))))
                .all();
    }
}
