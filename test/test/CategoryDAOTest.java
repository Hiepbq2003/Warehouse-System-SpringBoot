package test;

import context.DBContext;
import dao.CategoryDAO;
import model.Category;

import java.sql.Connection;
import java.util.List;

public class CategoryDAOTest {

    public static void main(String[] args) {
        try {
            Connection conn = new DBContext().getConnection();
            CategoryDAO dao = new CategoryDAO(conn);

            // 1. Thêm category mới
            Category newCat = new Category();
            newCat.setCategoryName("Thể loại Test");
            boolean added = dao.addCategory(newCat);
            System.out.println("Thêm category: " + (added ? "Thành công" : "Thất bại"));

            // 2. Lấy danh sách tất cả category
            List<Category> list = dao.getAllCategories();
            System.out.println("Danh sách tất cả category:");
            for (Category c : list) {
                System.out.printf("ID: %d, Tên: %s\n", c.getCategoryId(), c.getCategoryName());
            }

            // 3. Lấy category vừa thêm (theo tên)
            Category inserted = null;
            for (Category c : list) {
                if ("Thể loại Test".equals(c.getCategoryName())) {
                    inserted = c;
                    break;
                }
            }

            if (inserted == null) {
                System.out.println("Không tìm thấy category vừa thêm.");
                conn.close();
                return;
            }

            System.out.println("Lấy category theo ID: " + dao.getCategoryById(inserted.getCategoryId()));

            // 4. Cập nhật category
            Category updatedCat = new Category();
            updatedCat.setCategoryName("Thể loại Đã cập nhật");
            boolean updated = dao.updateCategory(inserted.getCategoryId(), updatedCat);
            System.out.println("Cập nhật category: " + (updated ? "Thành công" : "Thất bại"));

            // 5. Xóa category
            boolean deleted = dao.deleteCategory(inserted.getCategoryId());
            System.out.println("Xóa category: " + (deleted ? "Thành công" : "Thất bại"));

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
