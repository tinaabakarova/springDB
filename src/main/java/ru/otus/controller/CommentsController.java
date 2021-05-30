package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Comment;
import ru.otus.service.CommentsOperationsService;

@Controller
public class CommentsController {

    private final CommentsOperationsService commentsOperationsService;

    @Autowired
    public CommentsController(CommentsOperationsService commentsOperationsService) {
        this.commentsOperationsService = commentsOperationsService;
    }

    @GetMapping("/comments")
    public String listCommentsByBook(@RequestParam("id") Long id, Model model) {
        Iterable<Comment> comments = commentsOperationsService.getAllCommentsByBook(id);
        model.addAttribute("comments", comments);

        return "showComments";
    }
}
