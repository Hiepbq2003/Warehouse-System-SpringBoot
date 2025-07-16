package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    List<Warehouse> findAll();

    Optional<Warehouse> findById(Integer id);

    Warehouse save(Warehouse warehouse);

    Warehouse getById(Integer id);

    void update(Warehouse warehouse);

    void addProductToWarehouse(Integer warehouseId, Integer productId, Integer quantity);

    void removeProductFromWarehouse(Integer warehouseId, Integer productId);
}
