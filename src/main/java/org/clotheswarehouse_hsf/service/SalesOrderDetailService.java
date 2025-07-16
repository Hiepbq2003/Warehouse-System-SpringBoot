package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.SalesOrderDetail;

import java.util.List;

public interface SalesOrderDetailService {
    List<SalesOrderDetail> findBySalesOrderId(Integer salesOrderId);

    SalesOrderDetail save(SalesOrderDetail detail);

    void deleteById(Integer id);

    void delete(SalesOrderDetail detail);
}
