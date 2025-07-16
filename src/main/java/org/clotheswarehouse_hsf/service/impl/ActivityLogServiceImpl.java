package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.ActivityLog;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.repository.ActivityLogRepository;
import org.clotheswarehouse_hsf.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public Page<ActivityLog> filterLogs(Long userId, LocalDateTime start, LocalDateTime end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return activityLogRepository.findByUserAndDate(userId, start, end, pageable);
    }


    @Override
    public void save(ActivityLog log) {
        activityLogRepository.save(log);
    }

    @Override
    public List<ActivityLog> findAll() {
        return activityLogRepository.findAll();
    }

    @Override
    public ActivityLog findById(Integer id) {
        return activityLogRepository.findById(id).orElse(null);
    }

    @Override
    public void logActivity(User user,
                            ActivityLog.ActionType actionType,
                            String entityType,
                            Integer entityId,
                            String note) {

        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActionType(actionType);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setNote(note);
        log.setTimestamp(LocalDateTime.now());

        activityLogRepository.save(log);
    }
}
