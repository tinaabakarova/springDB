package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenresDao;
import ru.otus.domain.Genre;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenresDao genresDao;

    public GenreServiceImpl(GenresDao genresDao) {
        this.genresDao = genresDao;
    }

    @Override
    @Transactional
    public void createGenre(String name){
        genresDao.save(new Genre(name));
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Genre> getAllGenre(){
        return genresDao.findAll();
    }

    @Override
    @Transactional
    public void deleteGenre(Long id){
        genresDao.deleteById(id);
    }
}
