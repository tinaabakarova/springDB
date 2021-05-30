package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorsDao;
import ru.otus.domain.Author;

@Service
public class AuthorOperationService {
    private final AuthorsDao authorsDao;

    public AuthorOperationService(AuthorsDao authorsDao) {
        this.authorsDao = authorsDao;
    }

    @Transactional
    public void createAuthor(String name){
        authorsDao.save(new Author(name));
    }

    @Transactional(readOnly = true)
    public Iterable<Author> getAllAuthors(){
        return authorsDao.findAll();
    }

    @Transactional
    public void deleteAuthor(Long id){
        authorsDao.deleteById(id);
    }
}
