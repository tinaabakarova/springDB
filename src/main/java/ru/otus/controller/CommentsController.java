package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentsController {

    @GetMapping("/comments")
    public String listCommentsByBook(@RequestParam("id") String id, Model model) {
        model.addAttribute("id", id);
        return "showComments";
    }
}
