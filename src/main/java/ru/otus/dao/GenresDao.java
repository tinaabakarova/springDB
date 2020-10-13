package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenresDao {
    public void insert(String name);

    public Genre getById(long id);

    public Genre getIdByName(String name);

    public List<Genre> getAll();

    public void deleteById(long id);
}
