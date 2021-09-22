package ru.otus.service;

import ru.otus.domain.Book;

import java.sql.SQLException;
import java.util.Optional;

public interface BookService {
    String createBook(String name,
                    String author,
                    String genre);

    Iterable<Book> getAllBooks();

    String deleteBook(Long id);

    Optional<Book> getBookById(Long id);

    String updateBookById(Long id,
                        String name,
                        String author_name,
                        String genre_name);
}
