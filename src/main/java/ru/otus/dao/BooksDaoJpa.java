package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BooksDaoJpa implements BooksDao{

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

    @Override
    public Book findByName(String name) {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-books-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "where b.name = :name",
                Book.class);
        query.setParameter("name", name);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-books-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Optional<Book> book = Optional.ofNullable(em.find(Book.class, id));
        book.ifPresent(value -> em.remove(value));
        em.flush();
        em.clear();
    }
}
