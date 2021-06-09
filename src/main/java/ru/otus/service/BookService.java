package ru.otus.service;

import ru.otus.domain.Book;

import java.util.Optional;

public interface BookService {
    void createBook(String name,
                    String author,
                    String genre);

    Iterable<Book> getAllBooks();

    void deleteBook(Long id);

    Optional<Book> getBookById(Long id);

    Book updateBookById(Long id,
                        String name,
                        String author_name,
                        String genre_name);
}
