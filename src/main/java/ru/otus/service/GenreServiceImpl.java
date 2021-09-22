package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenresDao;
import ru.otus.domain.Genre;
import ru.otus.utils.Constants;

import java.util.ArrayList;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenresDao genresDao;

    public GenreServiceImpl(GenresDao genresDao) {
        this.genresDao = genresDao;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey="libraryKey", fallbackMethod="fallbackGenres")
    public Iterable<Genre> getAllGenre(){
        return genresDao.findAll();
    }

    private Iterable<Genre> fallbackGenres() {
        return new ArrayList<>(){{
            add(
                    Genre.builder()
                        .id(0L)
                        .name(Constants.NOT_AVAILABLE)
                        .build());
        }};
    }
}
