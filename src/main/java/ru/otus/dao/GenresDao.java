package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenresDao {

    public Optional<Genre> getById(long id);

    public Genre findByName(String name);

    public void deleteById(long id);

    public List<Genre> getAll();

    Genre save(Genre genre);
}
