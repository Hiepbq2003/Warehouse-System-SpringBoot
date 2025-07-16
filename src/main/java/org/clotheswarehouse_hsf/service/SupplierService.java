package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> findSuppliers(String searchTerm, int page, int pageSize);

    List<Supplier> getAll();

    int getTotalFilteredSuppliers(String searchTerm);

    Supplier findById(int id);

    boolean isSupplierNameExist(String name);

    boolean isEmailExist(String email);

    Supplier findBySupplierName(String name);

    Supplier findByEmail(String email);

    Supplier save(Supplier supplier);

    boolean update(Supplier supplier);

    boolean delete(int id);

    List<Supplier> findAll();

    List<Supplier> searchSuppliersByNameOrEmail(String keyword);
}
