package org.clotheswarehouse_hsf.model;

import org.clotheswarehouse_hsf.model.enums.StockInwardStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "stockinwards")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockInward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer stockInwardId;

     String inwardCode;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
     Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
     Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "purchase_request_id")
     PurchaseOrder purchaseOrder;

    @Column(columnDefinition = "TEXT")
     String notes;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime inwardDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "stockInward", cascade = CascadeType.ALL, orphanRemoval = true)
     List<StockInwardDetail> details;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    StockInwardStatus status;

}
