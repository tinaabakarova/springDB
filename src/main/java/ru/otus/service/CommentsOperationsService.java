package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BooksDao;
import ru.otus.dao.CommentsDao;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CommentsOperationsService {
    private final CommentsDao commentsDao;
    private final BooksDao booksDao;

    public CommentsOperationsService(CommentsDao commentsDao, BooksDao booksDao) {
        this.commentsDao = commentsDao;
        this.booksDao = booksDao;
    }

    @Transactional
    public void createComment(String comment,
                              String bookName,
                              String userName) {
        Book book = booksDao.findByName(bookName);
        commentsDao.save(new Comment(comment, book, userName));
    }

    @Transactional(readOnly = true)
    public Iterable<Comment> getAllCommentsByBook(Long id) {
        Book book = booksDao.findById(id).orElseThrow(EntityNotFoundException::new);
        return book.getComments();
    }

    @Transactional(readOnly = true)
    public Iterable<Comment> getAllComments() {
        return commentsDao.findAll();
    }

    @Transactional
    public void deleteComment(Long id) {
        commentsDao.deleteById(id);
    }

    @Transactional
    public Comment updateCommentById(Long id,
                                  String commentString) {
        Optional<Comment> comment = commentsDao.findById(id);
        Comment comment1 = comment.orElseThrow(EntityNotFoundException::new);
        comment1.setComment(commentString);
        return commentsDao.save(comment1);
    }
}
