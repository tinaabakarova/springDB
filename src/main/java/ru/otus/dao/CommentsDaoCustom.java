package ru.otus.dao;

import ru.otus.domain.Comment;

import java.util.List;

public interface CommentsDaoCustom {
   public List<Comment> findAllByBookId(String id);
}
