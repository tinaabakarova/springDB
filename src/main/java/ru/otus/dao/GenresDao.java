package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Genre;

public interface GenresDao extends CrudRepository<Genre, Long> {

    public Genre findByName(String name);
}
