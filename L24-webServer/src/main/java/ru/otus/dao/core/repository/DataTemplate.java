package ru.otus.dao.core.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface DataTemplate<T> {
    Optional<T> findById(Session session, long id);

    List<T> findAll(Session session);

    void insert(Session session, T object);

    void update(Session session, T object);
}
