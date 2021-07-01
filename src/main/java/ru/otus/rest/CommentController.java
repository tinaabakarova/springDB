package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.dao.CommentsDao;
import ru.otus.dto.CommentDTO;


@RestController
public class CommentController {
    private final CommentsDao commentsDao;

    @Autowired
    public CommentController(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    @GetMapping("/api/comments")
    @Transactional(readOnly = true)
    public Flux<CommentDTO> getAllComments(@RequestParam("id") String id) {
        return commentsDao.findAllByBookId(id).map(CommentDTO::new);
    }
}
