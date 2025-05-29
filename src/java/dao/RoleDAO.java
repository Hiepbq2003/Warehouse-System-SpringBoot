package dao;

import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private Connection conn;

    public RoleDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Role> getAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT role_id, role_name FROM Roles";  // CSDL của bạn có bảng Roles
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                roles.add(role);
            }
        }
        return roles;
    }
    public Role fromString(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name không được để trống");
        }

        Role role = new Role();

        switch (roleName.trim().toLowerCase()) {
            case "admin":
                role.setRoleId(1);
                role.setRoleName("admin");
                break;
            case "user":
                role.setRoleId(2);
                role.setRoleName("user");
                break;
            // thêm các role khác nếu có
            default:
                throw new IllegalArgumentException("Vai trò không hợp lệ: " + roleName);
        }

        return role;
    }
}
