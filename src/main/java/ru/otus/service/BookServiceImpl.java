package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorsDao;
import ru.otus.dao.BooksDao;
import ru.otus.dao.GenresDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import ru.otus.exception.EntityNotFoundException;
import ru.otus.utils.Constants;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private DataSource dataSource;
    private final BooksDao booksDao;
    private final AuthorsDao authorsDao;
    private final GenresDao genresDao;

    public BookServiceImpl(BooksDao booksDao, AuthorsDao authorsDao, GenresDao genresDao) {
        this.authorsDao = authorsDao;
        this.genresDao = genresDao;
        this.booksDao = booksDao;
    }

    @Override
    @Transactional()
    @HystrixCommand(commandKey = "libraryKey", fallbackMethod = "returnFallbackErrorCreate")
    public String createBook(String name,
                             String author,
                             String genre) {
        booksDao.save(new Book(name, authorsDao.findByName(author), genresDao.findByName(genre)));
        return Constants.SUCCESSFULL_OPERATION;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "libraryKey", fallbackMethod = "fallbackBooks")
    public Iterable<Book> getAllBooks() {
        return booksDao.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    @HystrixCommand(commandKey = "libraryKey", fallbackMethod = "returnFallbackError")
    public String deleteBook(Long id) {
        booksDao.deleteById(id);
        return Constants.SUCCESSFULL_OPERATION;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "libraryKey", fallbackMethod = "fallbackOptionalBook")
    public Optional<Book> getBookById(Long id) {
        return booksDao.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    @HystrixCommand(commandKey = "libraryKey", fallbackMethod = "returnFallbackErrorUpdate")
    public String updateBookById(Long id,
                                 String name,
                                 String author_name,
                                 String genre_name) {
        Author author = authorsDao.findByName(author_name);
        Genre genre = genresDao.findByName(genre_name);

        Optional<Book> bookOptional = booksDao.findById(id);
        Book book = bookOptional.orElseThrow(EntityNotFoundException::new);
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        booksDao.save(book);
        return Constants.SUCCESSFULL_OPERATION;
    }

    private Iterable<Book> fallbackBooks() {
        return new ArrayList<>() {{
            add(
                    Book.builder().id(0L)
                        .name(Constants.NOT_AVAILABLE)
                        .author(new Author(Constants.NOT_AVAILABLE))
                        .genre(new Genre(Constants.NOT_AVAILABLE))
                        .build()
            );
        }};
    }

    private Optional<Book> fallbackOptionalBook(Long id) {
        Book book = Book.builder().id(id)
                .name(Constants.NOT_AVAILABLE)
                .author(new Author(Constants.NOT_AVAILABLE))
                .genre(new Genre(Constants.NOT_AVAILABLE))
                .build();
        return Optional.of(book);
    }

    public String returnFallbackError(Long id) {
        return Constants.HYSTRICS_ERROR_MESSAGE;
    }

    public String returnFallbackErrorCreate(String name,
                                            String author_name,
                                            String genre_name) {
        return Constants.HYSTRICS_ERROR_MESSAGE;
    }


    public String returnFallbackErrorUpdate(Long id,
                                            String name,
                                            String author_name,
                                            String genre_name) {
        return Constants.HYSTRICS_ERROR_MESSAGE;
    }
}
