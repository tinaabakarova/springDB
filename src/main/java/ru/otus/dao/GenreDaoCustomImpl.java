package ru.otus.dao;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Book;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
public class GenreDaoCustomImpl implements GenreDaoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public boolean isReferenceExistInRepository(String id) {
        List<Book> query = mongoTemplate.query(Book.class)
                .matching(query(where("genre").is(new DBRef("genre", new ObjectId(id)))))
                .all();

        return !query.isEmpty();
    }
}
