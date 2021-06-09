package ru.otus.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDTO;
import ru.otus.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/comments")
    public List<CommentDTO> getAllComments(@RequestParam("id") Long id) {
        Iterable<Comment> comments = commentService.getAllCommentsByBook(id);
        return StreamSupport.stream(comments.spliterator(), false)
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }
}
