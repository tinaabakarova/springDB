package ru.otus.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreDTO;
import ru.otus.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genres/all")
    public List<GenreDTO> getAllGenres() {
        Iterable<Genre> genres = genreService.getAllGenre();
        return StreamSupport.stream(genres.spliterator(), false)
                .map(GenreDTO::new)
                .collect(Collectors.toList());
    }
}
