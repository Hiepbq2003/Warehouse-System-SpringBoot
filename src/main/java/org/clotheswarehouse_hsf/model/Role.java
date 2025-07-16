package org.clotheswarehouse_hsf.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @Column(name = "role_id")
    String roleId;

    @Column(name = "role_name", nullable = false)
    String roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    List<User> users;

    public static Role fromString(String roleId) {
        if (roleId == null || roleId.trim().isEmpty()) {
            throw new IllegalArgumentException("roleId không được để trống");
        }

        Role role = new Role();
        switch (roleId.trim().toLowerCase()) {
            case "admin" -> {
                role.setRoleId("admin");
                role.setRoleName("Administrator");
            }
            case "warehouse_manage" -> {
                role.setRoleId("warehouse_manage");
                role.setRoleName("Warehouse Manager");
            }
            case "purchasing_staff" -> {
                role.setRoleId("purchasing_staff");
                role.setRoleName("Purchasing Staff");
            }
            case "warehouse_staff" -> {
                role.setRoleId("warehouse_staff");
                role.setRoleName("Warehouse Staff");
            }
            case "sales_staff" -> {
                role.setRoleId("sale_staff");
                role.setRoleName("Sale Staff");
            }
            default -> throw new IllegalArgumentException("Vai trò không hợp lệ: " + roleId);
        }
        return role;
    }
}
