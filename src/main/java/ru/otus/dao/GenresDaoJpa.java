package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenresDaoJpa implements GenresDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            em.flush();
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre findByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g " +
                        "from Genre g " +
                        "where g.name = :name",
                Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Optional<Genre> genre = Optional.ofNullable(em.find(Genre.class, id));
        genre.ifPresent(value -> em.remove(value));
    }
}
