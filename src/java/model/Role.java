package model;

public class Role {
    private int roleId;
    private String roleName;

    // getter và setter
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // fromString map tên role sang đối tượng Role
    public static Role fromString(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name không được để trống");
        }

        Role role = new Role();
        switch (roleName.trim().toLowerCase()) {
            case "customer":
                role.setRoleId(1);
                role.setRoleName("customer");
                break;
            case "staff":
                role.setRoleId(2);
                role.setRoleName("staff");
                break;
            case "manager":
                role.setRoleId(3);
                role.setRoleName("manager");
                break;
            case "admin":
                role.setRoleId(4);
                role.setRoleName("admin");
                break;
            default:
                throw new IllegalArgumentException("Vai trò không hợp lệ: " + roleName);
        }
        return role;
    }
}
