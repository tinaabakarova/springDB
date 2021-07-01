package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Book;
import ru.otus.dto.BookDTO;
import ru.otus.exception.EntityNotFoundException;


@RestController
public class BookController {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookController(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @GetMapping("/api/books/all")
    @Transactional(readOnly = true)
    public Flux<BookDTO> getAllBooks() {
        return  bookDao.findAll().map(BookDTO::new);
    }


    @DeleteMapping("/api/books")
    @Transactional
    public Mono<Void> deleteBookById(@RequestParam("id") String id) {
        return bookDao.deleteById(id);
    }


    @GetMapping("/api/books")
    @Transactional(readOnly = true)
    public Mono<BookDTO> getBook(@RequestParam("id") String id) {
        return bookDao.findById(id).map(BookDTO::new).doOnError(EntityNotFoundException::new);
    }

    @PutMapping("/api/books")
    @Transactional
    public  Mono<Book>  updateBook(@RequestBody BookDTO bookDTO) {
        return bookDao.findById(bookDTO.getId())
                .zipWith(authorDao.findByName(bookDTO.getAuthorName()),
                        (b, a) -> {
                            b.setName(bookDTO.getName());
                            b.setAuthor(a);
                            return b;
                        })
                .zipWith(genreDao.findByName(bookDTO.getGenreName()),
                        (b, g) -> {
                            b.setGenre(g);
                            return b;
                        })
                .flatMap(bookDao::save);
    }

    @PostMapping("/api/books")
    @Transactional
    public  Mono<Book>  saveBook(@RequestBody BookDTO bookDTO) {
        return Mono.just(new Book())
                .zipWith(authorDao.findByName(bookDTO.getAuthorName()),
                        (b, a) -> {
                            b.setName(bookDTO.getName());
                            b.setAuthor(a);
                            return b;
                        })
                .zipWith(genreDao.findByName(bookDTO.getGenreName()),
                        (b, g) -> {
                            b.setGenre(g);
                            return b;
                        })
                .flatMap(bookDao::save);
    }
}
