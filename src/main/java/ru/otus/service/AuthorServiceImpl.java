package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorsDao;
import ru.otus.domain.Author;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorsDao authorsDao;

    public AuthorServiceImpl(AuthorsDao authorsDao) {
        this.authorsDao = authorsDao;
    }

    @Override
    @Transactional
    public void createAuthor(String name){
        authorsDao.save(new Author(name));
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Author> getAllAuthors(){
        return authorsDao.findAll();
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id){
        authorsDao.deleteById(id);
    }
}
