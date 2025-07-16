package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Integer> {
    @Query("SELECT a FROM ActivityLog a WHERE "
            + "(:userId IS NULL OR a.user.userId = :userId) "
            + "AND (:start IS NULL OR a.timestamp >= :start) "
            + "AND (:end IS NULL OR a.timestamp <= :end)")
    Page<ActivityLog> findByUserAndDate(@Param("userId") Long userId,
                                        @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end,
                                        Pageable pageable);


}
