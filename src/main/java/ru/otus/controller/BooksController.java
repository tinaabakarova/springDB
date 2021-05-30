package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Book;
import ru.otus.service.BookOperationsService;

@Controller
public class BooksController {

    private final BookOperationsService bookOperationsService;

    @Autowired
    public BooksController(BookOperationsService bookOperationsService) {
        this.bookOperationsService = bookOperationsService;
    }

    @GetMapping("/")
    public String listBooks(Model model) {
        Iterable<Book> books = bookOperationsService.getAllBooks();
        model.addAttribute("books", books);

        return "main";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") Long id, Model model) {
        Book book = bookOperationsService.getBookById(id).get();
        model.addAttribute("book", book);
        return "editBook";
    }
    
    @PostMapping("/edit")
    public String saveBook(Book book,
                           @RequestParam("authorName")String authorName,
                           @RequestParam("genreName")String genreName) {
        bookOperationsService.updateBookById(book.getId(), book.getName(), authorName, genreName);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") Long id) {
        bookOperationsService.deleteBook(id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        return "createBook";
    }

    @PostMapping("/create")
    public String saveCreatedBook(Book book,
                           @RequestParam("authorName")String authorName,
                           @RequestParam("genreName")String genreName) {
        bookOperationsService.createBook(book.getName(), authorName, genreName);
        return "redirect:/";
    }
}
