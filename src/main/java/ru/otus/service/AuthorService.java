package ru.otus.service;

import ru.otus.domain.Author;

public interface AuthorService {
    Iterable<Author> getAllAuthors();
}
