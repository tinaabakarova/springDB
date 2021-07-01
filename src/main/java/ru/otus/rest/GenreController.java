package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.dao.GenreDao;
import ru.otus.dto.GenreDTO;


@RestController
public class GenreController {
    private final GenreDao genreDao;

    @Autowired
    public GenreController(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @GetMapping("/api/genres/all")
    @Transactional(readOnly = true)
    public Flux<GenreDTO> getAllGenres() {
        return genreDao.findAll().map(GenreDTO::new);
    }
}
