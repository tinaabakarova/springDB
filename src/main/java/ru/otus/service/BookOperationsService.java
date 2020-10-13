package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.otus.dao.BooksDao;
import ru.otus.domain.Book;

@ShellComponent
public class BookOperationsService {
    private final BooksDao booksDao;
    private final IoService ioService;

    public BookOperationsService(BooksDao booksDao, IoService ioService) {
        this.booksDao = booksDao;
        this.ioService = ioService;
    }

    @ShellMethod(key = "create-book", value = "Create a book in DB")
    public void createBook(@ShellOption({"name"})String name,
                           @ShellOption({"author"})String author,
                           @ShellOption({"genre"})String genre){
        booksDao.insert(name, author, genre);
    }

    @ShellMethod(key = "show-books", value = "Show all books in DB")
    public void showAllBooks(){
        booksDao.getAll().forEach(book -> ioService.out(book.toString()));
    }

    @ShellMethod(key = "delete-book", value = "Delete a book in DB")
    public void deleteBook(@ShellOption({"id"})long id){
        booksDao.deleteById(id);
    }

    @ShellMethod(key = "show-book", value = "Show book by id")
    public void showBookById(@ShellOption({"id"})long id){
        ioService.out(booksDao.getById(id).toString());
    }

    @ShellMethod(key = "update-book", value = "Update book by id")
    public void updateBookById(@ShellOption({"id"})long id,
                               @ShellOption({"name"})String name,
                               @ShellOption({"author"})String author,
                               @ShellOption({"genre"})String genre){
        Book book = new Book(id, name, author, genre);
        booksDao.update(book);
    }
}
