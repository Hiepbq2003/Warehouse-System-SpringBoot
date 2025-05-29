package controller;

import context.DBContext;
import dao.UserDAO;
import model.User;
import utils.PasswordUtil;
import utils.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private DBContext dbContext;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            dbContext = new DBContext();  // Tạo DBContext (kết nối DB)
            userDAO = new UserDAO(dbContext.getConnection()); // Truyền connection cho DAO
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo DBContext hoặc UserDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (SessionUtil.isUserLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String roleStr = request.getParameter("role");

        if (username == null || username.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()
                || fullName == null || fullName.trim().isEmpty()
                || roleStr == null || roleStr.trim().isEmpty()) {

            SessionUtil.setErrorMessage(request, "Vui lòng điền đầy đủ thông tin");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        if (!isValidEmail(email.trim())) {
            SessionUtil.setErrorMessage(request, "Định dạng email không hợp lệ");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        if (!password.equals(confirmPassword)) {
            SessionUtil.setErrorMessage(request, "Mật khẩu xác nhận không khớp");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        if (!PasswordUtil.isValidPassword(password)) {
            SessionUtil.setErrorMessage(request, "Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ và số");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        if (username.length() < 3 || username.length() > 50) {
            SessionUtil.setErrorMessage(request, "Tên đăng nhập phải từ 3-50 ký tự");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        try {
            if (userDAO.isUsernameExist(username.trim())) {
                SessionUtil.setErrorMessage(request, "Tên đăng nhập đã tồn tại");
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            }

            if (userDAO.findByEmail(email.trim()) != null) {
                SessionUtil.setErrorMessage(request, "Email đã được sử dụng");
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            }

            int roleId;
            try {
                roleId = Integer.parseInt(roleStr.trim());
            } catch (NumberFormatException e) {
                SessionUtil.setErrorMessage(request, "Vai trò không hợp lệ");
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            }

            String hashedPassword = PasswordUtil.hashPassword(password);
            User newUser = new User();
            newUser.setUsername(username.trim());
            newUser.setPasswordHash(hashedPassword);
            newUser.setFullName(fullName.trim());
            newUser.setEmail(email.trim());
            newUser.setRoleId(roleId);
            newUser.setActive(true); // Mặc định active
            long now = System.currentTimeMillis();
            newUser.setCreatedAt(new java.sql.Timestamp(now));
            newUser.setUpdatedAt(new java.sql.Timestamp(now));

            boolean success = userDAO.add(newUser);
            if (success) {
                SessionUtil.setSuccessMessage(request, "Đăng ký thành công! Vui lòng đăng nhập");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                SessionUtil.setErrorMessage(request, "Có lỗi xảy ra trong quá trình đăng ký");
                response.sendRedirect(request.getContextPath() + "/register");
            }
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            SessionUtil.setErrorMessage(request, "Có lỗi xảy ra trong quá trình đăng ký");
            response.sendRedirect(request.getContextPath() + "/register");
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
