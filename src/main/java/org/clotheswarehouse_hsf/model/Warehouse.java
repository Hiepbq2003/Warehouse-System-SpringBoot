package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    Integer warehouseId;

    @Column(name = "warehouse_name", length = 100, nullable = false)
    String warehouseName;

    @Column(name = "address", columnDefinition = "TEXT", nullable = false)
    String address;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    List<Inventory> inventories;

}
