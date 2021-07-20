package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorDTO;
import ru.otus.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/authors/all")
    public List<AuthorDTO> getAllAuthors() {
        Iterable<Author> authors = authorService.getAllAuthors();
        return StreamSupport.stream(authors.spliterator(), false)
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
    }
}
