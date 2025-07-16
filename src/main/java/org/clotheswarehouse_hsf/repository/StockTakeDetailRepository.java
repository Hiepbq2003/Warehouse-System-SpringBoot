package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.StockTakeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTakeDetailRepository extends JpaRepository<StockTakeDetail, Integer> {
}
