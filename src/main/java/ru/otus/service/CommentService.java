package ru.otus.service;

import ru.otus.domain.Comment;

public interface CommentService {
    void createComment(String comment,
                       String bookName,
                       String userName);

    Iterable<Comment> getAllCommentsByBook(Long id);

    Iterable<Comment> getAllComments();

    void deleteComment(Long id);

    Comment updateCommentById(Long id,
                              String commentString);
}
