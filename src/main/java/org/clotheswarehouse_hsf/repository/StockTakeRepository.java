package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.StockTake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTakeRepository extends JpaRepository<StockTake, Integer> {
}
