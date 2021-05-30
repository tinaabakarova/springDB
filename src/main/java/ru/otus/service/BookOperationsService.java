package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorsDao;
import ru.otus.dao.BooksDao;
import ru.otus.dao.GenresDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class BookOperationsService {
    private final BooksDao booksDao;
    private final AuthorsDao authorsDao;
    private final GenresDao genresDao;

    public BookOperationsService(BooksDao booksDao, AuthorsDao authorsDao, GenresDao genresDao) {
        this.authorsDao = authorsDao;
        this.genresDao = genresDao;
        this.booksDao = booksDao;
    }

    @Transactional
    public void createBook(String name,
                           String author,
                           String genre) {
        booksDao.save(new Book(name, authorsDao.findByName(author), genresDao.findByName(genre)));
    }

    @Transactional(readOnly = true)
    public Iterable<Book> getAllBooks() {
        return booksDao.findAll();
    }

    @Transactional
    public void deleteBook(Long id) {
        booksDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return booksDao.findById(id);
    }

    @Transactional
    public Book updateBookById(Long id,
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
        return booksDao.save(book);
    }
}
