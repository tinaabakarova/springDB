package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Book;

public interface BooksDao extends CrudRepository<Book, Long> {
    public Book findByName(String name);
}
