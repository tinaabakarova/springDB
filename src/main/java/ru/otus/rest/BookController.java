package ru.otus.rest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.dto.BookDTO;
import ru.otus.exception.EntityNotFoundException;
import ru.otus.service.BookService;
import ru.otus.utils.Constants;

import java.sql.SQLException;
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

    @DeleteMapping("/api/books")
    public String deleteBookById(@RequestParam("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/api/books")
    public BookDTO getBook(@RequestParam("id") Long id) {
        return new BookDTO(bookService.getBookById(id).orElseThrow(EntityNotFoundException::new));
    }

    @PutMapping("/api/books")
    public String updateBook(@RequestBody BookDTO bookDTO){
       return bookService.updateBookById(bookDTO.getId(), bookDTO.getName(), bookDTO.getAuthorName(), bookDTO.getGenreName());
    }

    @PostMapping("/api/books")
    public String saveBook(@RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO.getName(), bookDTO.getAuthorName(), bookDTO.getGenreName());
    }
}
