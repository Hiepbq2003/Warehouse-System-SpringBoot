package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
    java.util.List<PurchaseOrderDetail> findByPurchaseOrderId(Integer orderId);
}
