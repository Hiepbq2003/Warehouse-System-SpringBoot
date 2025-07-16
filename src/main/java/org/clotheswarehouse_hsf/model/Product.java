package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Integer productId;

    @Column(name = "product_code", nullable = false, unique = true)
    String productCode;

    @Column(name = "product_name", nullable = false)
    String productName;

    String description;

    @Column(name = "purchase_price")
    Float purchasePrice;

    @Column(name = "sale_price")
    Float salePrice;

    @Column(name = "supplier_id")
    Integer supplierId;

    @ManyToOne
    @JoinColumn(name = "supplier_id", insertable = false, updatable = false)
    Supplier supplier;

    @Column(name = "low_stock_threshold")
    Integer lowStockThreshold;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "is_active")
    Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    public String getUnit() {
        return category != null ? category.getUnit() : null;
    }

}
