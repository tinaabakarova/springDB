package ru.otus.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.domain.Comment;

public interface CommentsDao extends ReactiveMongoRepository<Comment, String>, CommentsDaoCustom {}
