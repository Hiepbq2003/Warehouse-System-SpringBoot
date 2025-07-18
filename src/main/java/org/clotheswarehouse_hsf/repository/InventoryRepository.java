package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.model.Product;
import org.clotheswarehouse_hsf.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse);

    List<Inventory> findByWarehouse(Warehouse warehouse);

    List<Inventory> findByProduct(Product product);

    @Query(value = """
                SELECT COUNT(*) FROM inventory i
                JOIN products p ON i.product_id = p.product_id
                WHERE (:name IS NULL OR LOWER(p.product_name) LIKE %:name%)
                AND (:warehouseId IS NULL OR i.warehouse_id = :warehouseId)
                AND (
                    :stock IS NULL OR
                    (:stock = 'enough' AND i.quantity_on_hand > 10) OR
                    (:stock = 'low' AND i.quantity_on_hand BETWEEN 1 AND 10) OR
                    (:stock = 'out' AND i.quantity_on_hand = 0)
                )
            """, nativeQuery = true)
    int countFiltered(@Param("name") String name,
                      @Param("warehouseId") Integer warehouseId,
                      @Param("stock") String stock);

    @Query(value = """
                SELECT i.inventory_id, i.product_id, i.warehouse_id, i.quantity_on_hand, i.last_updated
                FROM inventory i
                JOIN products p ON i.product_id = p.product_id
                WHERE (:name IS NULL OR LOWER(p.product_name) LIKE %:name%)
                AND (:warehouseId IS NULL OR i.warehouse_id = :warehouseId)
                AND (
                    :stock IS NULL OR
                    (:stock = 'enough' AND i.quantity_on_hand > 10) OR
                    (:stock = 'low' AND i.quantity_on_hand BETWEEN 1 AND 10) OR
                    (:stock = 'out' AND i.quantity_on_hand = 0)
                )
                LIMIT :size OFFSET :offset
            """, nativeQuery = true)
    List<Inventory> filterWithPaging(@Param("name") String name,
                                     @Param("warehouseId") Integer warehouseId,
                                     @Param("stock") String stock,
                                     @Param("offset") int offset,
                                     @Param("size") int size);

    @Query("SELECT i FROM Inventory i WHERE i.product.productId = :productId AND i.warehouse.warehouseId = :warehouseId")
    Optional<Inventory> findByProductIdAndWarehouseId(@Param("productId") Integer productId, @Param("warehouseId") Integer warehouseId);

    @Query("SELECT i FROM Inventory i WHERE i.warehouse.warehouseId = :warehouseId " +
            "AND (:productName IS NULL OR LOWER(i.product.productName) LIKE %:productName%)")
    List<Inventory> findByWarehouseIdAndProductName(@Param("warehouseId") Integer warehouseId,
                                                    @Param("productName") String productName,
                                                    Pageable pageable);

    @Query("SELECT COUNT(i) FROM Inventory i WHERE i.warehouse.warehouseId = :warehouseId " +
            "AND (:productName IS NULL OR LOWER(i.product.productName) LIKE %:productName%)")
    int countByWarehouseIdAndProductName(@Param("warehouseId") Integer warehouseId,
                                         @Param("productName") String productName);

    Optional<Inventory> findByProduct_ProductIdAndWarehouse_WarehouseId(Integer productId, Integer warehouseId);

}
