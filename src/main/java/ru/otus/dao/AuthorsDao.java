package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Author;

public interface AuthorsDao extends CrudRepository<Author, Long> {

    public Author findByName(String name);
}
