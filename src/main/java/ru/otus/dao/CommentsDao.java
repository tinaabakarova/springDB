package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Comment;

public interface CommentsDao extends CrudRepository<Comment, Long> {

}
