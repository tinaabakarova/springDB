package ru.otus.service;

import ru.otus.domain.Genre;

public interface GenreService {
    void createGenre(String name);

    Iterable<Genre> getAllGenre();

    void deleteGenre(Long id);
}
