package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({CommentDaoJpa.class})
class CommentsDaoTest {
    @Autowired
    CommentDaoJpa commentDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном комментарие по его id")
    @Test
    void getById() {
        Comment comment = em.find(Comment.class, 1L);
        assertThat(commentDaoJpa.getById(1)).isPresent().get().isEqualTo(comment);
    }

    @DisplayName("должен загружать список всех комментариев по книге с полной информацией о них")
    @Test
    void getAllByBook() {
        Book book = em.find(Book.class, 1L);
        Comment comment = em.find(Comment.class, 1L);
        List<Comment> comments = commentDaoJpa.getAll(book);
        assertThat(comments).containsAll(List.of(comment));
    }

    @DisplayName("должен загружать список всех комментариев с полной информацией о них")
    @Test
    void getAll() {
        Comment comment1 = em.find(Comment.class, 1L);
        Comment comment2 = em.find(Comment.class, 2L);
        List<Comment> comments = commentDaoJpa.getAll();
        assertThat(comments).containsAll(List.of(comment1, comment2));
    }

    @DisplayName(" должен удалять заданный комментарий по его id")
    @Test
    void deleteById() {
        Comment comment = em.find(Comment.class, 1L);
        assertThat(comment).isNotNull();
        em.detach(comment);
        commentDaoJpa.deleteById(1);
        Comment deleted = em.find(Comment.class,1L);
        assertThat(deleted).isNull();
    }

    @DisplayName(" должен корректно сохранять всю информацию об комментарии")
    @Test
    void save() {
        Book book = em.find(Book.class, 1L);
        Comment comment = new Comment(3, "cool", book, "user");
        commentDaoJpa.save(comment);
        assertThat(comment).isEqualTo(em.find(Comment.class, 3L));
    }
}