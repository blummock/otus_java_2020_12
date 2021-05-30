package ru.otus.dao;

import ru.otus.dao.crm.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findByLogin(String login);
}
