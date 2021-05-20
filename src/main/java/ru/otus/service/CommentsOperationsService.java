package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BooksDao;
import ru.otus.dao.CommentsDao;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.Optional;

@ShellComponent
public class CommentsOperationsService {
    private final CommentsDao commentsDao;
    private final BooksDao booksDao;
    private final IoService ioService;

    public CommentsOperationsService(CommentsDao commentsDao, BooksDao booksDao, IoService ioService) {
        this.commentsDao = commentsDao;
        this.booksDao = booksDao;
        this.ioService = ioService;
    }

    @Transactional
    @ShellMethod(key = "create-comment", value = "Create a comment in DB")
    public void createComment(@ShellOption({"comment"})String comment, @ShellOption({"bookName"})String bookName,
                              @ShellOption({"userName"})String userName){
        Book book = booksDao.findByName(bookName);
        commentsDao.save(new Comment(comment, book, userName));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-comments-by-book", value = "Show all comments by book in DB")
    public void showAllCommentsByBook(@ShellOption({"bookName"})String bookName){
        Book book = booksDao.findByName(bookName);
        book.getComments().forEach(comment -> ioService.out(comment.toString()));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-comments", value = "Show all comments in DB")
    public void showAllComments(){
        commentsDao.findAll().forEach(comment -> ioService.out(comment.toString()));
    }

    @Transactional
    @ShellMethod(key = "delete-comment", value = "Delete an comment in DB")
    public void deleteComment(@ShellOption({"id"})long id){
        commentsDao.deleteById(id);
    }

    @Transactional
    @ShellMethod(key = "update-comment", value = "Update comment by id")
    public void updateCommentById(@ShellOption({"id"})long id,
                               @ShellOption({"comment"})String commentString) {
        Optional<Comment> comment = commentsDao.findById(id);
        if (comment.isPresent()){
            Comment comment1 = comment.get();
            comment1.setComment(commentString);
            commentsDao.save(comment1);
        } else {
            ioService.out("No comment found in database by this Id.");
        }
    }
}
