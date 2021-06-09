package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Comment;
import ru.otus.service.CommentService;
import ru.otus.service.CommentServiceImpl;

@Controller
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public String listCommentsByBook(@RequestParam("id") Long id, Model model) {
        Iterable<Comment> comments = commentService.getAllCommentsByBook(id);
        model.addAttribute("comments", comments);

        return "showComments";
    }
}
