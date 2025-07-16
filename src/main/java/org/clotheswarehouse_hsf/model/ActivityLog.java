package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "activity_logs")
@Data
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    ActionType actionType;

    @Column(name = "entity_type", length = 50)
    String entityType;

    @Column(name = "entity_id")
    Integer entityId;

    @Column(name = "old_value", columnDefinition = "TEXT")
    String oldValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    String newValue;

    @Column(name = "note", length = 255)
    String note;

    @Column(name = "timestamp")
    LocalDateTime timestamp;

    public enum ActionType {
        CREATE, UPDATE, DELETE, ADJUST, LOGIN, LOGOUT
    }
}
