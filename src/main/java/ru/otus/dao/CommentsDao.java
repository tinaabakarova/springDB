package ru.otus.dao;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentsDao {
    public Optional<Comment> getById(long id);

    public List<Comment> getAll(Book book);

    public List<Comment> getAll();

    public void deleteById(long id);

    Comment save(Comment comment);
}
