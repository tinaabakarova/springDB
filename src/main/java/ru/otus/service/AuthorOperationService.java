package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorsDao;
import ru.otus.domain.Author;

@ShellComponent
public class AuthorOperationService {
    private final AuthorsDao authorsDao;
    private final IoService ioService;

    public AuthorOperationService(AuthorsDao authorsDao, IoService ioService) {
        this.authorsDao = authorsDao;
        this.ioService = ioService;
    }

    @Transactional
    @ShellMethod(key = "create-author", value = "Create a author in DB")
    public void createAuthor(@ShellOption({"name"})String name){
        authorsDao.save(new Author(name));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "show-authors", value = "Show all authors in DB")
    public void showAllAuthors(){
        authorsDao.getAll().forEach(author -> ioService.out(author.toString()));
    }

    @Transactional
    @ShellMethod(key = "delete-author", value = "Delete an author in DB")
    public void deleteAuthor(@ShellOption({"id"})long id){
        authorsDao.deleteById(id);
    }
}
