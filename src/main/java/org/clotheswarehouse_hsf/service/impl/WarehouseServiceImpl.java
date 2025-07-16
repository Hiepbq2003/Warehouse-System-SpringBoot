package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.model.Product;
import org.clotheswarehouse_hsf.model.Warehouse;
import org.clotheswarehouse_hsf.repository.InventoryRepository;
import org.clotheswarehouse_hsf.repository.ProductRepository;
import org.clotheswarehouse_hsf.repository.WarehouseRepository;
import org.clotheswarehouse_hsf.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public void update(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Override
    public Optional<Warehouse> findById(Integer id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse getById(Integer id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kho có id = " + id));
    }

    @Override
    public void addProductToWarehouse(Integer warehouseId, Integer productId, Integer quantity) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Kho không tồn tại"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        Optional<Inventory> existingInventory = inventoryRepository.findByProductIdAndWarehouseId(productId, warehouseId);
        if (existingInventory.isPresent()) {
            throw new RuntimeException("Sản phẩm đã có trong kho rồi");
        }

        Inventory inventory = Inventory.builder()
                .warehouse(warehouse)
                .product(product)
                .quantityOnHand(quantity)
                .lastUpdated(new Timestamp(System.currentTimeMillis()))
                .build();

        inventoryRepository.save(inventory);
    }


    @Override
    public void removeProductFromWarehouse(Integer warehouseId, Integer productId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Kho không tồn tại"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        inventoryRepository.findByProductAndWarehouse(product, warehouse)
                .ifPresent(inventoryRepository::delete);
    }
}
