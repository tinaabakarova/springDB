package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenresDao;
import ru.otus.domain.Genre;

@ShellComponent
public class GenreOperationService {
    private final GenresDao genresDao;
    private final IoService ioService;

    public GenreOperationService(GenresDao genresDao, IoService ioService) {
        this.genresDao = genresDao;
        this.ioService = ioService;
    }

    @Transactional
    @ShellMethod(key = "create-genre", value = "Create a genre in DB")
    public void createGenre(@ShellOption({"name"})String name){
        genresDao.save(new Genre(name));
    }

    @Transactional
    @ShellMethod(key = "show-genres", value = "Show all genres in DB")
    public void showAllGenre(){
        genresDao.getAll().forEach(genre -> ioService.out(genre.toString()));
    }

    @Transactional
    @ShellMethod(key = "delete-genre", value = "Delete an genre in DB")
    public void deleteGenre(@ShellOption({"id"})long id){
        genresDao.deleteById(id);
    }
}
