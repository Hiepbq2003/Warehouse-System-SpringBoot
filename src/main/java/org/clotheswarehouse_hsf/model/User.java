package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false, name = "password_hash")
    String passwordHash;

    @Column(nullable = false, name = "full_name")
    String fullName;

    String email;
    String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    Role role;

    @Column(name = "created_at")
    Timestamp createdAt;

    @Column(name = "updated_at")
    Timestamp updatedAt;

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "last_login")
    LocalDateTime lastLogin;

    @Column(name = "last_logout")
    LocalDateTime lastLogout;
}
