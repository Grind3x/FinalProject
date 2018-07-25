package domain.service;

import java.util.List;

public interface GenericService<T> {

    List<T> findAll();

    List<T> findAllLimit(int first, int max);

    T findById(Long id);

    void insert(T t);

    void update(T t);

    void remove(T t);

    int count();
}
