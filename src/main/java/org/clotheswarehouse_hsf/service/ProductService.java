package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    List<Product> findActiveProducts();

    Product getById(int id);

    void save(Product product);

    void deleteById(int id);

    boolean existsByProductCode(String productCode);

    boolean isProductCodeExists(String code);

    List<Product> searchWithFilter(String keyword, Boolean active, int page, int pageSize);

    int countSearchWithFilter(String keyword, Boolean active);

    List<Product> getActiveProducts();

    List<Product> findByProductCodes(List<String> codes);

    List<Product> findAll();

    boolean existsByProductName(String name);

    long count();
}
