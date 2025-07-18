package org.clotheswarehouse_hsf.service.impl;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.StockInward;
import org.clotheswarehouse_hsf.model.enums.StockInwardStatus;
import org.clotheswarehouse_hsf.repository.StockInwardRepository;
import org.clotheswarehouse_hsf.service.StockInwardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockInwardServiceImpl implements StockInwardService {

    private final StockInwardRepository stockInwardRepository;

    @Override
    public List<StockInward> findAll() {
        return stockInwardRepository.findAll();
    }

    @Override
    public Optional<StockInward> findById(Integer id) {
        return stockInwardRepository.findById(id);
    }

    @Override
    public StockInward save(StockInward stockInward) {
        if (stockInward.getStatus() == null) {
            stockInward.setStatus(StockInwardStatus.DRAFT);
        }
        if (stockInward.getCreatedAt() == null) {
            stockInward.setCreatedAt(LocalDateTime.now());
        }
        return stockInwardRepository.save(stockInward);
    }

    @Override
    public void deleteById(Integer id) {
        stockInwardRepository.deleteById(id);
    }

    @Override
    public void updateStatus(Integer id, StockInwardStatus status) {
        StockInward inward = stockInwardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu nhập"));
        inward.setStatus(status);
        stockInwardRepository.save(inward);
    }
    @Override
    public List<StockInward> findByStatus(StockInwardStatus status) {
        return stockInwardRepository.findByStatus(status);
    }
    @Override
    public Page<StockInward> filter(
            StockInwardStatus status,
            String inwardCode,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        return stockInwardRepository.filter(
                status,
                inwardCode,
                startDate,
                endDate,
                pageable
        );
    }
    @Override
    public Page<StockInward> pageByStatus(StockInwardStatus status, Pageable pageable) {
        return stockInwardRepository.findAllByStatus(status, pageable);
    }
    @Override
    public Page<StockInward> filterByManager(
            StockInwardStatus status,
            String inwardCode,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer userId,
            Integer supplierId,
            Pageable pageable) {

        return stockInwardRepository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();

            if (status != null)
                predicates = cb.and(predicates, cb.equal(root.get("status"), status));

            if (inwardCode != null && !inwardCode.isEmpty())
                predicates = cb.and(predicates, cb.like(root.get("inwardCode"), "%" + inwardCode + "%"));

            if (startDate != null)
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));

            if (endDate != null)
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("createdAt"), endDate));

            if (userId != null)
                predicates = cb.and(predicates, cb.equal(root.get("user").get("userId"), userId));

            if (supplierId != null)
                predicates = cb.and(predicates, cb.equal(root.get("supplier").get("supplierId"), supplierId));

            return predicates;
        }, pageable);
    }

}
