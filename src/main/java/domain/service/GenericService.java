package domain.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {

    List<T> findAll();

    List<T> findAllLimit(int first, int max);

    Optional<T> findById(Long id);

    void insert(T t);

    void update(T t);

    void remove(T t);

    int count();
}
