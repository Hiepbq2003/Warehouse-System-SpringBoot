package org.clotheswarehouse_hsf.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LowStockProduct {
    private Integer productId;
    private String productCode;
    private String productName;
    private String description;
    private String unit;
    private Float purchasePrice;
    private Float salePrice;
    private Integer lowStockThreshold;
    private Integer quantityOnHand;
    private String supplierName;
    private String status; // "LOW_STOCK" hoặc "OUT_OF_STOCK"

    public String getStatusDisplay() {
        if (quantityOnHand == null || quantityOnHand <= 0) {
            return "Hết hàng";
        } else if (quantityOnHand <= lowStockThreshold) {
            return "Sắp hết hàng";
        }
        return "Đủ hàng";
    }

    public String getStatusClass() {
        if (quantityOnHand == null || quantityOnHand <= 0) {
            return "bg-danger";
        } else if (quantityOnHand <= lowStockThreshold) {
            return "bg-warning";
        }
        return "bg-success";
    }
}
