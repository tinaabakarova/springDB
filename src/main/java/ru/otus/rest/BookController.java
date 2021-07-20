package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.dto.BookDTO;
import ru.otus.exception.EntityNotFoundException;
import ru.otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books/all")
    public List<BookDTO> getAllBooks() {
        Iterable<Book> books = bookService.getAllBooks();
        return StreamSupport.stream(books.spliterator(), false)
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/books")
    public void deleteBookById(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/api/books")
    public BookDTO getBook(@RequestParam("id") Long id) {
        return new BookDTO(bookService.getBookById(id).orElseThrow(EntityNotFoundException::new));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/books")
    public void updateBook(@RequestBody BookDTO bookDTO) {
        bookService.updateBookById(bookDTO.getId(), bookDTO.getName(), bookDTO.getAuthorName(), bookDTO.getGenreName());
    }

    @PostMapping("/api/books")
    public void saveBook(@RequestBody BookDTO bookDTO) {
        bookService.createBook(bookDTO.getName(), bookDTO.getAuthorName(), bookDTO.getGenreName());
    }
}
