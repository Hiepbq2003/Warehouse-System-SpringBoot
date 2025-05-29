package controller;

import context.DBContext;
import dao.RoleDAO;
import dao.UserDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;
import model.User;

@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;
    private RoleDAO roleDAO;  
    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            DBContext dbContext = new DBContext();
            conn = dbContext.getConnection();
            userDAO = new UserDAO(conn);
            roleDAO = new RoleDAO(conn);  // tạo DAO tương tự UserDAO
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> userList = userDAO.getAllUsers();
            List<Role> roleList = roleDAO.getAllRoles();

            req.setAttribute("userList", userList);
            req.setAttribute("roleList", roleList);

            req.getRequestDispatcher("/manageUser/ManageUser.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

