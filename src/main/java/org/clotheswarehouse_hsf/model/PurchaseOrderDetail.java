package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "purchase_order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_detail_id")
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    PurchaseOrder purchaseOrder;

    @Column(name = "product_id", nullable = false)
    Integer productId;

    @Column(name = "requested_quantity", nullable = false)
    Integer requestedQuantity;

    @Column(name = "unit_price_estimated")
    BigDecimal unitPriceEstimated;

    @Column(name = "supplier_id_suggested")
    Integer supplierIdSuggested;

    @Column(name = "note", columnDefinition = "TEXT")
    String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;

}
