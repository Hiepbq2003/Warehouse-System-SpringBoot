package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.sql.Date;

@Entity
@Table(name = "stock_takes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_take_id")
    private Integer stockTakeId;

    @Column(name = "stock_take_code", nullable = false, unique = true)
    private String stockTakeCode;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "stock_take_date", nullable = false)
    private Date stockTakeDate;

    @Column(name = "status", nullable = false)
    private String status; // pending, in_progress, completed, reconciled

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    // Các field chỉ dùng để hiển thị (không mapping DB)
    @Transient
    private String userFullName;

    @Transient
    private Integer totalProducts;

    @Transient
    private Integer completedProducts;
}
