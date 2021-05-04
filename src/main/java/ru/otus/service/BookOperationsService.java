package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorsDao;
import ru.otus.dao.BooksDao;
import ru.otus.dao.GenresDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.Optional;

@ShellComponent
public class BookOperationsService {
    private final BooksDao booksDao;
    private final AuthorsDao authorsDao;
    private final GenresDao genresDao;
    private final IoService ioService;

    public BookOperationsService(BooksDao booksDao, AuthorsDao authorsDao, GenresDao genresDao, IoService ioService) {
        this.authorsDao = authorsDao;
        this.genresDao = genresDao;
        this.booksDao = booksDao;
        this.ioService = ioService;
    }

    @Transactional
    @ShellMethod(key = "create-book", value = "Create a book in DB")
    public void createBook(@ShellOption({"name"})String name,
                           @ShellOption({"author"})String author,
                           @ShellOption({"genre"})String genre){
        booksDao.save(new Book(name, authorsDao.findByName(author), genresDao.findByName(genre)));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-books", value = "Show all books in DB")
    public void showAllBooks(){
        booksDao.findAll().forEach(book -> ioService.out(book.toString()));
    }

    @Transactional
    @ShellMethod(key = "delete-book", value = "Delete a book in DB")
    public void deleteBook(@ShellOption({"id"})long id){
        booksDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-book", value = "Show book by name")
    public void showBookByName(@ShellOption({"name"})String name){
        ioService.out(booksDao.findByName(name).toString());
    }

    @Transactional
    @ShellMethod(key = "update-book", value = "Update book by id")
    public void updateBookById(@ShellOption({"id"})long id,
                               @ShellOption({"name"})String name,
                               @ShellOption({"author"})String author_name,
                               @ShellOption({"genre"})String genre_name){
        Author author = authorsDao.findByName(author_name);
        Genre genre = genresDao.findByName(genre_name);

        Optional<Book> bookOptional = booksDao.findById(id);
        if (bookOptional.isPresent()){
            Book book = bookOptional.get();
            book.setName(name);
            book.setAuthor(author);
            book.setGenre(genre);
            booksDao.save(book);
        } else {
            ioService.out("No book found in database by this Id.");
        }
    }
}
