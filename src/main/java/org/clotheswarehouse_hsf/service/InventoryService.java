package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.model.Product;
import org.clotheswarehouse_hsf.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    long getTotalQuantityOnHand();

    List<Inventory> findAll();

    Optional<Inventory> findById(Integer id);

    Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse);

    List<Inventory> findByWarehouse(Warehouse warehouse);

    List<Inventory> findByProduct(Product product);

    Inventory save(Inventory inventory);

    void deleteById(Integer id);

    int countFilteredInventories(String productName, Integer warehouseId, String stockStatus);

    List<Inventory> findFilteredInventories(String productName, Integer warehouseId, String stockStatus, int page, int size);

    List<Inventory> findByWarehouseId(Integer warehouseId);

    int countByWarehouseIdAndProductName(Integer warehouseId, String productName);

    List<Inventory> findByWarehouseIdAndProductName(Integer warehouseId, String productName, int page, int size);

    Optional<Inventory> findByProductIdAndWarehouseId(Integer productId, Integer warehouseId);

    long countOutOfStock();
    long countLowStock();
}
