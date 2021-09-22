package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorsDao;
import ru.otus.domain.Author;
import ru.otus.utils.Constants;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorsDao authorsDao;

    public AuthorServiceImpl(AuthorsDao authorsDao) {
        this.authorsDao = authorsDao;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey="libraryKey", fallbackMethod="fallbackAuthors")
    public Iterable<Author> getAllAuthors(){
        return authorsDao.findAll();
    }

    private Iterable<Author> fallbackAuthors() {
        return new ArrayList<>() {{
            add(
                    Author.builder()
                        .id(0L)
                        .name(Constants.NOT_AVAILABLE)
                        .build());
        }};
    }

    private String returnFallbackError(){
        return Constants.HYSTRICS_ERROR_MESSAGE;
    }
}
