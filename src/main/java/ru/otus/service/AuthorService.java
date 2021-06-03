package ru.otus.service;

import ru.otus.domain.Author;

public interface AuthorService {
    void createAuthor(String name);

    Iterable<Author> getAllAuthors();

    void deleteAuthor(Long id);
}
