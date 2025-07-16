package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryService {
    Page<Category> searchCategories(String search, PageRequest pageRequest);

    List<Category> findAll();

    Category findById(int id);

    boolean existsByName(String name);

    void create(String name);

    boolean existsByNameExceptId(String name, int excludeId);

    void update(int id, String name);

    void delete(int id);

    boolean isInUse(int id);

    Category findByName(String name);
}
