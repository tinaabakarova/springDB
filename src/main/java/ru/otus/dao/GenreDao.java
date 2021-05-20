package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Genre;

public interface GenreDao extends MongoRepository<Genre, String>, GenreDaoCustom{
    public Genre findByName(String name);
}
