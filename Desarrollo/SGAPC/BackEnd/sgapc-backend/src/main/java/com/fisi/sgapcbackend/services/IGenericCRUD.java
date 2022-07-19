package com.fisi.sgapcbackend.services;


public interface IGenericCRUD <T,I> {
    public T save(T t);
    public T findById(I id);
    public T update(T t, I id);
    public void deleteById(I id);

}