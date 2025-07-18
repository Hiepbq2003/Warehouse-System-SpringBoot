package org.clotheswarehouse_hsf.controller.admin;

import org.clotheswarehouse_hsf.model.ActivityLog;
import org.clotheswarehouse_hsf.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping("/admin/Activity/activity-logs")
    public String viewActivityLogs(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {

        Page<ActivityLog> logs = activityLogService.filterLogs(userId, start, end, page, size);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", logs.getTotalPages());
        model.addAttribute("userId", userId);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "admin/Activity/activity-logs";
    }
}
