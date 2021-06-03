package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDTO;
import ru.otus.exception.EntityNotFoundException;
import ru.otus.service.*;

@Controller
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BooksController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String listBooks(Model model) {
        Iterable<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        return "main";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(EntityNotFoundException::new);
        Iterable<Author> authors = authorService.getAllAuthors();
        Iterable<Genre> genres = genreService.getAllGenre();
        model.addAttribute("bookDTO", new BookDTO(book));
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("action", "/edit");
        model.addAttribute("submitOption", "Edit");

        return "bookOperations";
    }
    
    @PostMapping("/edit")
    public String saveBook(BookDTO bookDTO) {
        bookService.updateBookById(bookDTO.getId(), bookDTO.getName(), bookDTO.getAuthorName(), bookDTO.getGenreName());
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        Iterable<Author> authors = authorService.getAllAuthors();
        Iterable<Genre> genres = genreService.getAllGenre();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("action", "/create");
        model.addAttribute("submitOption", "Create");
        return "bookOperations";
    }

    @PostMapping("/create")
    public String saveCreatedBook(BookDTO bookDTO) {
        bookService.createBook(bookDTO.getName(), bookDTO.getAuthorName(), bookDTO.getGenreName());
        return "redirect:/";
    }
}
