package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Comment;

public interface CommentsDao extends MongoRepository<Comment, String>, CommentsDaoCustom {}
