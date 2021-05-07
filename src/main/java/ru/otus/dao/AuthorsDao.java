package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorsDao {

    public Optional<Author> getById(long id);

    public Author findByName(String name);

    public void deleteById(long id);

    public List<Author> getAll();

    Author save(Author author);
}
