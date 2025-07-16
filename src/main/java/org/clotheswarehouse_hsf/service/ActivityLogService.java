package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.clotheswarehouse_hsf.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogService {
    void save(ActivityLog log);

    List<ActivityLog> findAll();

    ActivityLog findById(Integer id);

    Page<ActivityLog> filterLogs(Long userId, LocalDateTime start, LocalDateTime end, int page, int size);

    void logActivity(User user,
                     ActivityLog.ActionType actionType,
                     String entityType,
                     Integer entityId,
                     String note);
}
