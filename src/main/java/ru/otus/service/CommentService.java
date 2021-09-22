package ru.otus.service;

import ru.otus.domain.Comment;

public interface CommentService {

    Iterable<Comment> getAllCommentsByBook(Long id);
}
