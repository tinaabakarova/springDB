package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.dao.AuthorDao;
import ru.otus.dto.AuthorDTO;

@RestController
public class AuthorController {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @GetMapping("/api/authors/all")
    @Transactional(readOnly = true)
    public Flux<AuthorDTO> getAllAuthors() {
        return authorDao.findAll().map(AuthorDTO::new);
    }
}
