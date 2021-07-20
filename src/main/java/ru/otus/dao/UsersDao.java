package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.User;

import java.util.Optional;

public interface UsersDao extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
