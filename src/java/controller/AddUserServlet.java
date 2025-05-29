package controller;

import context.DBContext;
import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

@WebServlet(name = "AddUserServlet", urlPatterns = {"/AddUserServlet"})
public class AddUserServlet extends HttpServlet {

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String roleStr = request.getParameter("roleId");

        if (password == null || password.trim().isEmpty()) {
            response.getWriter().println("Password không được để trống!");
            return;
        }

        int roleId;
        try {
            roleId = Integer.parseInt(roleStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("RoleId không hợp lệ!");
            return;
        }

        String hashedPassword;
        try {
            hashedPassword = hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            response.getWriter().println("Lỗi mã hóa password!");
            return;
        }

        // Tạo đối tượng user
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRoleId(roleId);
        user.setActive(true); // mặc định là đang hoạt động
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        try {
            DBContext dbContext = new DBContext();
            boolean success = new UserDAO(dbContext.getConnection()).add(user);
            dbContext.close();

            if (success) {
                response.sendRedirect("UserServlet");
            } else {
                response.getWriter().println("❌ Thêm user thất bại, có thể username đã tồn tại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("❌ Lỗi kết nối DB: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/manageUser/ManageUser.jsp").forward(request, response);
    }
}
