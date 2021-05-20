package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Author;

public interface AuthorDao extends MongoRepository<Author, String>, AuthorDaoCustom {
    public Author findByName(String name);
}
