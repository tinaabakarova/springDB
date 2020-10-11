package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorsDao {
    public void insert(String name);

    public Author getById(long id);

    public Author getIdByName(String name);

    public void deleteById(long id);

    public List<Author> getAll();
}
