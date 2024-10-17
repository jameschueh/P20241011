package com.systex.P20240930.service;

import java.util.Optional;

public abstract class GenericService<T, ID> {

    public abstract Optional<T> findById(ID id);

    public abstract boolean register(T entity);

    public abstract T login(String account, String password);

    public abstract boolean update(T entity);
}

