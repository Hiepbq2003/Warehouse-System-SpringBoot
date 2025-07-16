package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer>,
        JpaSpecificationExecutor<PurchaseOrder> {

    PurchaseOrder findByRequestCode(String requestCode);
}
