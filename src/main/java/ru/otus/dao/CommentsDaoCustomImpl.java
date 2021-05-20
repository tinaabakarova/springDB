package ru.otus.dao;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
public class CommentsDaoCustomImpl implements CommentsDaoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Comment> findAllByBookId(String id) {
        return mongoTemplate.query(Comment.class)
                .matching(query(where("book").is(new DBRef("book", new ObjectId(id)))))
                .all();
    }
}
