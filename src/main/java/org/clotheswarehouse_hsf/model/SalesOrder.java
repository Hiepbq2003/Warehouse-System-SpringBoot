package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sales_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer salesOrderId;

    @Column(length = 50)
    String orderCode;

    @Column(length = 100)
    String customerName;

    @Column(length = 100)
    String customerEmail;

    @Column(length = 20)
    String customerPhone;

    @Column(columnDefinition = "TEXT")
    String shippingAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    User createdBy;

    LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    OrderStatus status;

    @Column(columnDefinition = "TEXT")
    String notes;

    LocalDateTime createdAt;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SalesOrderDetail> orderDetails = new ArrayList<>();

    public enum OrderStatus {
        pending_stock_check,
        awaiting_shipment,
        shipped,
        completed,
        cancelled
    }

    public java.math.BigDecimal getTotalOrderValue() {
        if (orderDetails == null) return java.math.BigDecimal.ZERO;

        return orderDetails.stream()
                .map(detail -> {
                    Float unitPrice = detail.getUnitSalePrice();
                    Integer quantity = detail.getQuantityOrdered();

                    if (unitPrice != null && quantity != null) {
                        return java.math.BigDecimal.valueOf(unitPrice * quantity);
                    } else {
                        return java.math.BigDecimal.ZERO;
                    }
                })
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
