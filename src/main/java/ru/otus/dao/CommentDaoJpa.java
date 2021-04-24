package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentsDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAll(Book book) {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-comments-entity-graph");
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.book b where b.name = :name", Comment.class);
        query.setParameter("name", book.getName());
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Comment where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Comment> getAll(){
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-comments-entity-graph");
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.book b", Comment.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }
}
