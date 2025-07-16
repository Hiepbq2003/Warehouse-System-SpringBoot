package org.clotheswarehouse_hsf.service.impl;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.PurchaseOrderDetail;
import org.clotheswarehouse_hsf.repository.PurchaseOrderDetailRepository;
import org.clotheswarehouse_hsf.service.PurchaseOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {

    private final PurchaseOrderDetailRepository detailRepository;

    @Override
    public List<PurchaseOrderDetail> findByOrderId(Integer orderId) {
        return detailRepository.findByPurchaseOrderId(orderId);
    }

    @Override
    public PurchaseOrderDetail save(PurchaseOrderDetail detail) {
        return detailRepository.save(detail);
    }

    @Override
    public void deleteById(Integer id) {
        detailRepository.deleteById(id);
    }
}
