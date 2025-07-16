package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.SalesOrderDetail;
import org.clotheswarehouse_hsf.repository.SalesOrderDetailRepository;
import org.clotheswarehouse_hsf.service.SalesOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderDetailServiceImpl implements SalesOrderDetailService {

    @Autowired
    private SalesOrderDetailRepository repository;

    @Override
    public List<SalesOrderDetail> findBySalesOrderId(Integer salesOrderId) {
        return repository.findBySalesOrder_SalesOrderId(salesOrderId);
    }

    @Override
    public SalesOrderDetail save(SalesOrderDetail detail) {
        return repository.save(detail);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(SalesOrderDetail detail) {
        repository.delete(detail);
    }

}
