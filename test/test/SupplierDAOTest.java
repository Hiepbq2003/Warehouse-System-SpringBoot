package test;

import context.DBContext;
import dao.SupplierDAO;
import model.Supplier;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class SupplierDAOTest {
    public static void main(String[] args) {
        DBContext dbContext = null;
        Connection conn = null;
        try {
            dbContext = new DBContext();
            conn = dbContext.getConnection();

            SupplierDAO dao = new SupplierDAO(conn);

            // 1. Thêm nhà cung cấp mới
            Supplier newSupplier = new Supplier();
            newSupplier.setSupplierName("Nhà cung cấp test");
            newSupplier.setContactPerson("Người liên hệ");
            newSupplier.setPhoneNumber("0123456789");
            newSupplier.setEmail("test@supplier.com");
            newSupplier.setAddress("123 Đường ABC");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            newSupplier.setCreatedAt(now);
            newSupplier.setUpdatedAt(now);
            boolean addResult = dao.addSupplier(newSupplier);
            System.out.println("Thêm nhà cung cấp: " + (addResult ? "Thành công" : "Thất bại"));

            // 2. Lấy danh sách nhà cung cấp
            List<Supplier> list = dao.getAllSuppliers();
            System.out.println("Danh sách nhà cung cấp:");
            for (Supplier s : list) {
                System.out.println(s.getSupplierId() + " - " + s.getSupplierName());
            }

            if (!list.isEmpty()) {
                // 3. Cập nhật nhà cung cấp đầu tiên
                Supplier first = list.get(0);
                first.setSupplierName("Nhà cung cấp cập nhật");
                first.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                boolean updateResult = dao.updateSupplier(first);
                System.out.println("Cập nhật nhà cung cấp: " + (updateResult ? "Thành công" : "Thất bại"));

                // 4. Xóa nhà cung cấp (nếu muốn test xóa)
                // boolean deleteResult = dao.deleteSupplier(first.getSupplierId());
                // System.out.println("Xóa nhà cung cấp: " + (deleteResult ? "Thành công" : "Thất bại"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbContext != null) {
                dbContext.close();
            }
        }
    }
}
