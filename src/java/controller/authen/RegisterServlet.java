package controller.authen;

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
import model.Role;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "form";
        }

        switch (action) {
            case "form":
                handleShowRegisterForm(request, response);
                break;
            default:
                handleShowRegisterForm(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "register";
        }

        switch (action) {
            case "register":
                handleRegisterUser(request, response);
                break;
            default:
                SessionUtil.setErrorMessage(request, "Hành động không hợp lệ");
                response.sendRedirect(request.getContextPath() + "/register");
                break;
        }
    }

    private void handleShowRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (SessionUtil.isUserLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        request.getRequestDispatcher("/view/authen/register.jsp").forward(request, response);
    }

    private void handleRegisterUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!validateRegistrationInput(request, response)) {
            return;
        }

        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String fullName = request.getParameter("fullName").trim();
        String roleStr = request.getParameter("role").trim();

        // Validate business rules
        if (!validateBusinessRules(request, response, username, email, password, roleStr)) {
            return;
        }

        // Check for existing username and email
        if (!checkUserExistence(request, response, username, email)) {
            return;
        }

        // Create and save user
        createAndSaveUser(request, response, username, email, phone, password, fullName, roleStr);
    }

    private boolean validateRegistrationInput(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

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
            return false;
        }

        if (!password.equals(confirmPassword)) {
            SessionUtil.setErrorMessage(request, "Mật khẩu xác nhận không khớp");
            response.sendRedirect(request.getContextPath() + "/register");
            return false;
        }

        return true;
    }

    private boolean validateBusinessRules(HttpServletRequest request, HttpServletResponse response,
                                        String username, String email, String password, String roleStr)
            throws IOException {

        if (!isValidEmail(email)) {
            SessionUtil.setErrorMessage(request, "Định dạng email không hợp lệ");
            response.sendRedirect(request.getContextPath() + "/register");
            return false;
        }

        if (!PasswordUtil.isValidPassword(password)) {
            SessionUtil.setErrorMessage(request, "Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ và số");
            response.sendRedirect(request.getContextPath() + "/register");
            return false;
        }

        if (username.length() < 3 || username.length() > 50) {
            SessionUtil.setErrorMessage(request, "Tên đăng nhập phải từ 3-50 ký tự");
            response.sendRedirect(request.getContextPath() + "/register");
            return false;
        }

//        try {
//            Integer.parseInt(roleStr);
//        } catch (NumberFormatException e) {
//            SessionUtil.setErrorMessage(request, "Vai trò không hợp lệ");
//            response.sendRedirect(request.getContextPath() + "/register");
//            return false;
//        }

        return true;
    }

    private boolean checkUserExistence(HttpServletRequest request, HttpServletResponse response,
                                     String username, String email) throws IOException {

        try {
            if (userDAO.isUsernameExist(username)) {
                SessionUtil.setErrorMessage(request, "Tên đăng nhập đã tồn tại");
                response.sendRedirect(request.getContextPath() + "/register");
                return false;
            }

            if (userDAO.findByEmail(email) != null) {
                SessionUtil.setErrorMessage(request, "Email đã được sử dụng");
                response.sendRedirect(request.getContextPath() + "/register");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error checking user existence: " + e.getMessage());
            SessionUtil.setErrorMessage(request, "Có lỗi xảy ra trong quá trình kiểm tra thông tin");
            response.sendRedirect(request.getContextPath() + "/register");
            return false;
        }
    }

    private void createAndSaveUser(HttpServletRequest request, HttpServletResponse response,
                                 String username, String email,String phone ,String password, String fullName, String roleStr)
            throws IOException {

        try {
           // int roleId = Integer.parseInt(roleStr);
            Role role = Role.fromString(roleStr);
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPasswordHash(hashedPassword);
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setRoleId(role.getRoleName());
            newUser.setActive(true);
            newUser.setPhone(phone);
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
