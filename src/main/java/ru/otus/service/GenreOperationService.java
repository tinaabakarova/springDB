package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenresDao;
import ru.otus.domain.Genre;

@Service
public class GenreOperationService {
    private final GenresDao genresDao;

    public GenreOperationService(GenresDao genresDao) {
        this.genresDao = genresDao;
    }

    @Transactional
    public void createGenre(String name){
        genresDao.save(new Genre(name));
    }

    @Transactional(readOnly = true)
    public Iterable<Genre> getAllGenre(){
        return genresDao.findAll();
    }

    @Transactional
    public void deleteGenre(Long id){
        genresDao.deleteById(id);
    }
}
