package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BooksController {

    @GetMapping("/")
    public String listBooks() {
        return "main";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") String id, Model model) {
        model.addAttribute("action", "/edit");
        model.addAttribute("submitOption", "Edit");
        model.addAttribute("id", id);
        return "bookOperations";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") String id, Model model) {
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
