package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.Category;
import org.clotheswarehouse_hsf.repository.CategoryRepository;
import org.clotheswarehouse_hsf.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> searchCategories(String search, PageRequest pageRequest) {
        return categoryRepository.findByNameContainingIgnoreCase(search == null ? "" : search, pageRequest);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        Optional<Category> optional = categoryRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public void create(String name) {
        Category category = Category.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
        categoryRepository.save(category);
    }

    @Override
    public boolean existsByNameExceptId(String name, int excludeId) {
        return categoryRepository.existsByNameIgnoreCaseAndIdNot(name, excludeId);
    }

    @Override
    public void update(int id, String name) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setName(name);
            categoryRepository.save(category);
        }
    }

    @Override
    public void delete(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public boolean isInUse(int id) {
        // Chưa có bảng Product, tạm trả về false
        return false;
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
