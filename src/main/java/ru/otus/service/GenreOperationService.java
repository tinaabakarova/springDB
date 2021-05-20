package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;
import ru.otus.utils.Messages;

@ShellComponent
public class GenreOperationService {
    private final GenreDao genreDao;
    private final IoService ioService;

    public GenreOperationService(GenreDao genreDao, IoService ioService) {
        this.genreDao = genreDao;
        this.ioService = ioService;
    }

    @Transactional
    @ShellMethod(key = "create-genre", value = "Create a genre in DB")
    public void createGenre(@ShellOption({"name"})String name){
        genreDao.save(new Genre(name));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-genres", value = "Show all genres in DB")
    public void showAllGenre(){
        genreDao.findAll().forEach(genre -> ioService.out(genre.toString()));
    }

    @Transactional
    @ShellMethod(key = "delete-genre", value = "Delete an genre in DB")
    public void deleteGenre(@ShellOption({"id"})String id){
        if (genreDao.isReferenceExistInRepository(id)) {
            ioService.out(Messages.DELETE_REFERENCE_ERROR);
        } else {
            genreDao.deleteById(id);
        }
    }
}
