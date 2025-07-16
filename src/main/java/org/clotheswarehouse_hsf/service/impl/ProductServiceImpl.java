package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.Product;
import org.clotheswarehouse_hsf.repository.ProductRepository;
import org.clotheswarehouse_hsf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByProductCode(String productCode) {
        return productRepository.existsByProductCode(productCode);
    }

    @Override
    public boolean isProductCodeExists(String code) {
        return productRepository.existsByProductCode(code);
    }

    @Override
    public List<Product> searchWithFilter(String keyword, Boolean active, int page, int pageSize) {
        int offset = (page < 1 ? 0 : (page - 1) * pageSize);
        return productRepository.searchFilterPaging(keyword, active, pageSize, offset);
    }

    @Override
    public int countSearchWithFilter(String keyword, Boolean active) {
        return productRepository.countSearchFilter(keyword, active);
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    @Override
    public List<Product> findByProductCodes(List<String> codes) {
        return productRepository.findByProductCodeIn(codes);
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        productRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public boolean existsByProductName(String name) {
        return productRepository.existsByProductName(name);
    }

    @Override
    public List<Product> findActiveProducts() {
        return productRepository.findByIsActive(true);
    }

}
