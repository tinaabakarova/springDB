package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;

public interface BooksDao {
    public void insert(String name, String author, String genre);

    public Book getById(long id);

    public List<Book> getAll();

    public void deleteById(long id);

    public void update(Book book);
}
