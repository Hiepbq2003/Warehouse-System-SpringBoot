package controller;

import context.DBContext;
import dao.SupplierDAO;
import model.Supplier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "ManageSupplierController", urlPatterns = {"/suppliers"})
public class ManageSupplierController extends HttpServlet {

    private SupplierDAO supplierDAO;
    private DBContext dbContext;

    @Override
    public void init() throws ServletException {
            Connection conn = dbContext.getConnection();
            supplierDAO = new SupplierDAO();
    }

    @Override
    public void destroy() {
        if (dbContext != null) {
            dbContext.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                handleListSuppliers(request, response);
                break;
            case "edit":
                handleShowEditForm(request, response);
                break;
            default:
                handleListSuppliers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                handleAddSupplier(request, response);
                break;
            case "edit":
                handleEditSupplier(request, response);
                break;
            case "delete":
                handleDeleteSupplier(request, response);
                break;
            default:
                handleListSuppliers(request, response);
                break;
        }
    }

    private void handleListSuppliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DBContext dbContext = null;
        
        try {
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            SupplierDAO supplierDAO = new SupplierDAO();
            List<Supplier> supplierList = supplierDAO.getAllSuppliers();

            request.setAttribute("supplierList", supplierList);
            request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", " Lỗi khi tải danh sách nhà cung cấp: " + e.getMessage());
            request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
        } finally {
            if (dbContext != null) {
                try {
                    dbContext.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleAddSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String contactPerson = request.getParameter("contactPerson");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Supplier supplier = new Supplier();
        supplier.setSupplierName(name);
        supplier.setContactPerson(contactPerson);
        supplier.setPhoneNumber(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        supplier.setCreatedAt(now);
        supplier.setUpdatedAt(now);

        DBContext dbContext = null;
        Connection conn = null;

        try {
            dbContext = new DBContext();
            conn = dbContext.getConnection();

            SupplierDAO supplierDAO = new SupplierDAO();
            boolean success = supplierDAO.addSupplier(supplier);

            if (success) {
                request.getSession().setAttribute("message", " Thêm nhà cung cấp thành công.");
                response.sendRedirect("suppliers");
            } else {
                request.setAttribute("error", " Không thể thêm nhà cung cấp.");
                request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", " Lỗi kết nối DB: " + e.getMessage());
            request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
        } finally {
            if (dbContext != null) {
                dbContext.close();
            }
        }
    }

    private void handleShowEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String supplierIdStr = request.getParameter("id");
        if (supplierIdStr == null || supplierIdStr.isEmpty()) {
            response.sendRedirect("suppliers");
            return;
        }

        DBContext dbContext = null;
        
        try {
            int supplierId = Integer.parseInt(supplierIdStr);
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            SupplierDAO supplierDAO = new SupplierDAO();
            Supplier supplier = supplierDAO.getSupplierById(supplierId);

            if (supplier == null) {
                request.getSession().setAttribute("message", " Không tìm thấy nhà cung cấp.");
                response.sendRedirect("suppliers");
                return;
            }

            request.setAttribute("supplier", supplier);
            request.getRequestDispatcher("/supplier/editSupplier.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", " ID nhà cung cấp không hợp lệ.");
            response.sendRedirect("suppliers");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", " Đã xảy ra lỗi: " + e.getMessage());
            request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
        } finally {
            if (dbContext != null) {
                dbContext.close();
            }
        }
    }

    private void handleEditSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DBContext dbContext = null;
        
        try {
            int supplierId = Integer.parseInt(request.getParameter("supplierId"));
            String name = request.getParameter("name");
            String contactPerson = request.getParameter("contactPerson");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");

            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            SupplierDAO supplierDAO = new SupplierDAO();
            Supplier supplier = supplierDAO.getSupplierById(supplierId);
            
            if (supplier == null) {
                request.getSession().setAttribute("message", " Không tìm thấy nhà cung cấp.");
                response.sendRedirect("suppliers");
                return;
            }

            supplier.setSupplierName(name);
            supplier.setContactPerson(contactPerson);
            supplier.setPhoneNumber(phone);
            supplier.setEmail(email);
            supplier.setAddress(address);
            supplier.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            boolean updated = supplierDAO.updateSupplier(supplier);
            if (updated) {
                request.getSession().setAttribute("message", " Cập nhật nhà cung cấp thành công.");
                response.sendRedirect("suppliers");
            } else {
                request.setAttribute("error", " Cập nhật nhà cung cấp thất bại!");
                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("/supplier/editSupplier.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", " ID nhà cung cấp không hợp lệ.");
            request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", " Đã xảy ra lỗi: " + e.getMessage());
            request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
        } finally {
            if (dbContext != null) {
                dbContext.close();
            }
        }
    }

    private void handleDeleteSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String supplierIdStr = request.getParameter("supplierId");
        if (supplierIdStr == null || supplierIdStr.isEmpty()) {
            request.getSession().setAttribute("message", " Không tìm thấy nhà cung cấp để xóa.");
            response.sendRedirect("suppliers");
            return;
        }

        DBContext dbContext = null;
        
        try {
            int supplierId = Integer.parseInt(supplierIdStr);
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            SupplierDAO supplierDAO = new SupplierDAO();
            boolean deleted = supplierDAO.deleteSupplier(supplierId);
            
            if (deleted) {
                request.getSession().setAttribute("message", " Xóa nhà cung cấp thành công.");
            } else {
                request.getSession().setAttribute("message", " Xóa nhà cung cấp thất bại.");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", " ID nhà cung cấp không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", " Đã xảy ra lỗi: " + e.getMessage());
        } finally {
            if (dbContext != null) {
                dbContext.close();
            }
        }
        
        response.sendRedirect("suppliers");
    }
}
