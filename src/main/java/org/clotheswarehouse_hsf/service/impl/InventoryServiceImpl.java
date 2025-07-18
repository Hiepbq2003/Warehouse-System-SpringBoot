package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.model.Product;
import org.clotheswarehouse_hsf.model.Warehouse;
import org.clotheswarehouse_hsf.repository.InventoryRepository;
import org.clotheswarehouse_hsf.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Optional<Inventory> findById(Integer id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse) {
        return inventoryRepository.findByProductAndWarehouse(product, warehouse);
    }

    @Override
    public List<Inventory> findByWarehouse(Warehouse warehouse) {
        return inventoryRepository.findByWarehouse(warehouse);
    }

    @Override
    public List<Inventory> findByProduct(Product product) {
        return inventoryRepository.findByProduct(product);
    }

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteById(Integer id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public int countFilteredInventories(String productName, Integer warehouseId, String stockStatus) {
        String nameFilter = (productName == null || productName.trim().isEmpty()) ? null : productName.trim().toLowerCase();
        String stockFilter = (stockStatus == null || stockStatus.trim().isEmpty()) ? null : stockStatus;

        return inventoryRepository.countFiltered(nameFilter, warehouseId, stockFilter);
    }

    @Override
    public List<Inventory> findFilteredInventories(String productName, Integer warehouseId, String stockStatus, int page, int size) {
        int offset = (page - 1) * size;
        String nameFilter = (productName == null || productName.trim().isEmpty()) ? null : productName.trim().toLowerCase();
        String stockFilter = (stockStatus == null || stockStatus.trim().isEmpty()) ? null : stockStatus;

        return inventoryRepository.filterWithPaging(nameFilter, warehouseId, stockFilter, offset, size);
    }

    @Override
    public List<Inventory> findByWarehouseId(Integer warehouseId) {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId(warehouseId);
        return inventoryRepository.findByWarehouse(warehouse);
    }

    @Override
    public List<Inventory> findByWarehouseIdAndProductName(Integer warehouseId, String productName, int page, int size) {
        page = Math.max(1, page);
        int offset = (page - 1) * size;
        String nameFilter = (productName == null || productName.trim().isEmpty()) ? null : productName.trim().toLowerCase();
        return inventoryRepository.findByWarehouseIdAndProductName(warehouseId, nameFilter, PageRequest.of(page - 1, size));
    }

    @Override
    public int countByWarehouseIdAndProductName(Integer warehouseId, String productName) {
        String nameFilter = (productName == null || productName.trim().isEmpty()) ? null : productName.trim().toLowerCase();
        return inventoryRepository.countByWarehouseIdAndProductName(warehouseId, nameFilter);
    }

    @Override
    public Optional<Inventory> findByProductIdAndWarehouseId(Integer productId, Integer warehouseId) {
        return inventoryRepository.findByProduct_ProductIdAndWarehouse_WarehouseId(productId, warehouseId);
    }

    @Override
    public long countOutOfStock() {
        return inventoryRepository.countOutOfStock();
    }

    @Override
    public long countLowStock() {
        return inventoryRepository.countLowStock();
    }

    @Override
    public long getTotalQuantityOnHand() {
        return inventoryRepository.getTotalQuantityOnHand();
    }
}
