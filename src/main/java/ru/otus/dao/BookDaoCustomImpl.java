package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Comment;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
public class BookDaoCustomImpl implements BookDaoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeCommentsByBookId(String id) {
        List<Comment> query = mongoTemplate.query(Comment.class)
                .matching(query(where("book.$id").is(new ObjectId(id))))
                .all();
        query.forEach(mongoTemplate::remove);
    }
}
