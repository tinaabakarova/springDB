package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String listBooks() {
        return "main";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("action", "/edit");
        model.addAttribute("submitOption", "Edit");
        model.addAttribute("id", id);
        return "bookOperations";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        model.addAttribute("action", "/create");
        model.addAttribute("submitOption", "Create");
        return "bookOperations";
    }
}
