package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BooksDao {
    public Book findByName(String name);

    public List<Book> getAll();

    public void deleteById(long id);

    Book save(Book book);

    public Optional<Book> getById(long id);
}
