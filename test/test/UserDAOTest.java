package test;
import context.DBContext;
import dao.UserDAO;
import model.User;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class UserDAOTest {

    public static void main(String[] args) {
        try {
            Connection conn = new DBContext().getConnection();
            UserDAO userDAO = new UserDAO(conn);

            // 1. Tạo user mới
            User user = new User();
            user.setUsername("testuser");
            user.setPasswordHash("123456");
            user.setFullName("Người Test");
            user.setRoleId(2); // role_id hợp lệ trong DB
            user.setEmail("testuser@example.com");
            user.setActive(true);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            user.setCreatedAt(now);
            user.setUpdatedAt(now);

            // Thêm user
            boolean added = userDAO.add(user);
            System.out.println("Add user: " + (added ? "Thành công" : "Thất bại"));

            // 2. Kiểm tra username tồn tại
            boolean exists = userDAO.isUsernameExist("testuser");
            System.out.println("Username tồn tại: " + exists);

            // 3. Lấy user theo username vừa tạo (lấy tất cả rồi tìm)
            List<User> allUsers = userDAO.getAllUsers();
            User userFromDB = null;
            for (User u : allUsers) {
                if ("testuser".equals(u.getUsername())) {
                    userFromDB = u;
                    break;
                }
            }
            if (userFromDB == null) {
                System.out.println("Không tìm thấy user vừa thêm.");
                conn.close();
                return;
            }

            System.out.println("Lấy user theo username: " + userFromDB.getFullName());

            // 4. Lấy user theo ID
            User userById = userDAO.getUserById(userFromDB.getUserId());
            System.out.println("Lấy user theo ID: " + (userById != null ? userById.getUsername() : "Không tìm thấy"));

            // 5. Cập nhật user
            userById.setFullName("Người Test Update");
            userById.setEmail("updated@example.com");
            boolean updated = userDAO.update(userById);
            System.out.println("Cập nhật user: " + (updated ? "Thành công" : "Thất bại"));

            // 6. Xóa user
            boolean deleted = userDAO.delete(userById.getUserId());
            System.out.println("Xóa user: " + (deleted ? "Thành công" : "Thất bại"));

            // 7. In danh sách tất cả user
            List<User> users = userDAO.getAllUsers();
            System.out.println("Danh sách tất cả user:");
            for (User u : users) {
                System.out.printf("ID: %d, Username: %s, FullName: %s, Email: %s, RoleId: %d, Active: %b\n",
                    u.getUserId(), u.getUsername(), u.getFullName(), u.getEmail(), u.getRoleId(), u.isActive());
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
