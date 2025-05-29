package controller;

import model.User;
import utils.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard", "/index", "/"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = SessionUtil.getUserFromSession(request);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(request, "Vui lòng đăng nhập để truy cập hệ thống");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
