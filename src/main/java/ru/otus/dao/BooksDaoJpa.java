package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BooksDaoJpa implements BooksDao{
    private GenresDao genresDao;
    private AuthorsDao authorsDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    public BooksDaoJpa(AuthorsDao authorsDao,
                       GenresDao genresDao)
    {
        this.authorsDao = authorsDao;
        this.genresDao = genresDao;
    }

    @Override
    public Book findByName(String name) {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-books-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b join fetch b.author join fetch b.genre " +
                        "where b.name = :name",
                Book.class);
        query.setParameter("name", name);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-books-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Book book){
        Author author = authorsDao.findByName(book.getAuthor().getName());
        Genre genre = genresDao.findByName(book.getGenre().getName());

        Query query = em.createQuery("update Book s " +
                "set s.name = :name, s.author = :author, s.genre = :genre " +
                "where s.id = :id");
        query.setParameter("name", book.getName());
        query.setParameter("author", author);
        query.setParameter("genre", genre);
        query.setParameter("id", book.getId());
        query.executeUpdate();
    }
}
