package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.Optional;

import static ru.otus.utils.Messages.ENTITY_NOT_FOUND;

@ShellComponent
public class BookOperationsService {
    private final BookDao bookDao;
    private final AuthorDao authorsDao;
    private final GenreDao genreDao;
    private final IoService ioService;

    public BookOperationsService(BookDao bookDao, AuthorDao authorsDao, GenreDao genreDao, IoService ioService) {
        this.authorsDao = authorsDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.ioService = ioService;
    }

    @Transactional
    @ShellMethod(key = "create-book", value = "Create a book in DB")
    public void createBook(@ShellOption({"name"})String name,
                           @ShellOption({"author"})String author,
                           @ShellOption({"genre"})String genre){
        bookDao.save(new Book(name, authorsDao.findByName(author), genreDao.findByName(genre)));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-books", value = "Show all books in DB")
    public void showAllBooks(){
        bookDao.findAll().forEach(book -> ioService.out(book.toString()));
    }

    @Transactional
    @ShellMethod(key = "delete-book", value = "Delete a book in DB")
    public void deleteBook(@ShellOption({"id"})String id){
        bookDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-book", value = "Show book by name")
    public void showBookByName(@ShellOption({"name"})String name){
        ioService.out(bookDao.findByName(name).toString());
    }

    @Transactional
    @ShellMethod(key = "update-book", value = "Update book by id")
    public void updateBookById(@ShellOption({"id"})String id,
                               @ShellOption({"name"})String name,
                               @ShellOption({"author"})String author_name,
                               @ShellOption({"genre"})String genre_name){
        Author author = authorsDao.findByName(author_name);
        Genre genre = genreDao.findByName(genre_name);

        Optional<Book> bookOptional = bookDao.findById(id);
        if (bookOptional.isPresent()){
            Book book = bookOptional.get();
            book.setName(name);
            book.setAuthor(author);
            book.setGenre(genre);
            bookDao.save(book);
        } else {
            ioService.out(ENTITY_NOT_FOUND);
        }
    }
}
