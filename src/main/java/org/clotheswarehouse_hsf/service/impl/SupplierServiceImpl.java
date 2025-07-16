package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.Supplier;
import org.clotheswarehouse_hsf.repository.SupplierRepository;
import org.clotheswarehouse_hsf.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> findSuppliers(String searchTerm, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return supplierRepository.searchWithPaging(searchTerm, offset, pageSize);
    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public int getTotalFilteredSuppliers(String searchTerm) {
        return supplierRepository.countFiltered(searchTerm);
    }

    @Override
    public Supplier findById(int id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isSupplierNameExist(String name) {
        return supplierRepository.existsBySupplierName(name);
    }

    @Override
    public boolean isEmailExist(String email) {
        return supplierRepository.existsByEmail(email);
    }

    @Override
    public Supplier findBySupplierName(String name) {
        return supplierRepository.findBySupplierName(name);
    }

    @Override
    public Supplier findByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public boolean update(Supplier supplier) {
        if (!supplierRepository.existsById(supplier.getSupplierId())) return false;
        supplierRepository.save(supplier);
        return true;
    }

    @Override
    public boolean delete(int id) {
        if (!supplierRepository.existsById(id)) return false;
        supplierRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public List<Supplier> searchSuppliersByNameOrEmail(String keyword) {
        return supplierRepository.searchByNameOrEmail(keyword);
    }
}
