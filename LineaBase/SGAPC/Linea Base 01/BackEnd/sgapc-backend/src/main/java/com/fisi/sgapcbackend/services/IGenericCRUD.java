package com.fisi.sgapcbackend.services;

import java.util.List;

public interface IGenericCRUD <T,I> {
    public List<T > getAll();
    public T findById(I id);
    public T save(T t);
    public void deleteById(I id);
}