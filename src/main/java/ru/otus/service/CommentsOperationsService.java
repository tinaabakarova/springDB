package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookDao;
import ru.otus.dao.CommentsDao;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.Optional;

import static ru.otus.utils.Messages.ENTITY_NOT_FOUND;

@ShellComponent
public class CommentsOperationsService {
    private final CommentsDao commentsDao;
    private final BookDao bookDao;
    private final IoService ioService;

    public CommentsOperationsService(CommentsDao commentsDao, BookDao bookDao, IoService ioService) {
        this.commentsDao = commentsDao;
        this.bookDao = bookDao;
        this.ioService = ioService;
    }

    @Transactional
    @ShellMethod(key = "create-comment", value = "Create a comment in DB")
    public void createComment(@ShellOption({"comment"})String comment, @ShellOption({"bookName"})String bookName,
                              @ShellOption({"userName"})String userName){
        Book book = bookDao.findByName(bookName);
        commentsDao.save(new Comment(comment, book, userName));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-comments-by-book", value = "Show all comments by book in DB")
    public void showAllCommentsByBook(@ShellOption({"bookName"})String bookName){
        Book book = bookDao.findByName(bookName);
        commentsDao.findAllByBookId(book.getId()).forEach(comment -> ioService.out(comment.toString()));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-comments", value = "Show all comments in DB")
    public void showAllComments(){
        commentsDao.findAll().forEach(comment -> ioService.out(comment.toString()));
    }

    @Transactional
    @ShellMethod(key = "delete-comment", value = "Delete an comment in DB")
    public void deleteComment(@ShellOption({"id"})String id){
        commentsDao.deleteById(id);
    }

    @Transactional
    @ShellMethod(key = "update-comment", value = "Update comment by id")
    public void updateCommentById(@ShellOption({"id"})String id,
                               @ShellOption({"comment"})String commentString) {
        Optional<Comment> comment = commentsDao.findById(id);
        if (comment.isPresent()){
            Comment comment1 = comment.get();
            comment1.setComment(commentString);
            commentsDao.save(comment1);
        } else {
            ioService.out(ENTITY_NOT_FOUND);
        }
    }
}
