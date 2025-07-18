package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.StockInwardDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockInwardDetailRepository extends JpaRepository<StockInwardDetail, Integer> {
    List<StockInwardDetail> findByStockInward_StockInwardId(Integer stockInwardId);
}
