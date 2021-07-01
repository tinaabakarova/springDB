package ru.otus.dao;

import reactor.core.publisher.Flux;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentsDaoCustom {
   Flux<Comment> findAllByBookId(String id);
}
