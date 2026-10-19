package com.mjc.school.repository;

import java.util.List;

public interface BaseRepository<T, K> {
    List<T> readAll();
    T readById(K id);
    T create(T entity);
    T update(T entity);
    boolean delete(K id);
}