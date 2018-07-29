package domain.service;

import domain.model.Category;

import java.util.Optional;

public interface CategoryService extends GenericService<Category> {
    Optional<Category> findByName(String name);

}
