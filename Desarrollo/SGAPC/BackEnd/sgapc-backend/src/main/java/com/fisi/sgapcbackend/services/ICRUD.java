package com.fisi.sgapcbackend.services;

import java.util.List;

public interface ICRUD <T,I> {
    public T save(T t);
    public T findById(I id);
    public List<T> getAll();
    public void deleteById(I id);
}
