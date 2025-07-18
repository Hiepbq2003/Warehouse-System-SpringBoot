package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "stockinwarddetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockInwardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inward_detail_id")
    Integer inwardDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_inward_id", nullable = false)
    StockInward stockInward;

    @Column(name = "product_id", nullable = false)
    Integer productId;

    @Column(name = "quantity_received", nullable = false)
    Integer quantityReceived;

    @Column(name = "unit_price_negotiated")
    BigDecimal unitPriceNegotiated;

    @Column(name = "unit_purchase_price")
    BigDecimal unitPurchasePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;
}
