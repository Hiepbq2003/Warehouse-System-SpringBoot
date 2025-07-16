package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    Integer inventoryId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    Warehouse warehouse;

    @Column(name = "quantity_on_hand")
    Integer quantityOnHand;

    @Column(name = "last_updated")
    Timestamp lastUpdated;

    public String getStatus() {
        if (product == null || product.getLowStockThreshold() == null || quantityOnHand == null) {
            return "Không xác định";
        }
        int threshold = product.getLowStockThreshold();
        if (quantityOnHand == 0) return "Hết hàng";
        else if (quantityOnHand <= threshold) return "Sắp hết";
        else return "Khả dụng";
    }
}
