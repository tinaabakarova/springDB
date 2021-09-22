package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.CommentsDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.utils.Constants;

import java.util.ArrayList;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentsDao commentsDao;

    public CommentServiceImpl(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "libraryKey", fallbackMethod = "fallbackComments")
    public Iterable<Comment> getAllCommentsByBook(Long id) {
        return commentsDao.findAllByBookId(id);
    }

    private Iterable<Comment> fallbackComments(Long id) {
        return new ArrayList<>() {{
            add(
                    Comment.builder()
                            .id(id)
                            .comment(Constants.NOT_AVAILABLE)
                            .userName(Constants.NOT_AVAILABLE)
                            .book(new Book(Constants.NOT_AVAILABLE,
                                    new Author(Constants.NOT_AVAILABLE),
                                    new Genre(Constants.NOT_AVAILABLE)))
                            .build()
            );
        }};
    }
}
