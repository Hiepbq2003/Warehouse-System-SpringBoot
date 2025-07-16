package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.PurchaseOrderDetail;

import java.util.List;

public interface PurchaseOrderDetailService {
    List<PurchaseOrderDetail> findByOrderId(Integer orderId);

    PurchaseOrderDetail save(PurchaseOrderDetail detail);

    void deleteById(Integer id);
}
