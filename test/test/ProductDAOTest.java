package test;

import context.DBContext;
import dao.CategoryDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.util.List;

public class ProductDAOTest {

    public static void main(String[] args) {
        try (Connection conn = new DBContext().getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            ProductDAO productDAO = new ProductDAO(conn);

            // Lấy danh sách category có sẵn
            List<Category> categories = categoryDAO.getAllCategories();
            if (categories.isEmpty()) {
                System.out.println("Không có category nào trong DB để dùng.");
                return;
            }
            Category category = categories.get(0); // lấy category đầu tiên

            // 1. Thêm sản phẩm mới với category có sẵn
            Product newProduct = new Product();
            newProduct.setProductCode("SP001");
            newProduct.setProductName("Sản phẩm test");
            newProduct.setDescription("Mô tả sản phẩm test");
            newProduct.setUnit("Cái");
            newProduct.setPurchasePrice(100000);
            newProduct.setSalePrice(120000);
            newProduct.setSupplierId(1); // nhớ kiểm tra supplier có tồn tại hoặc sửa theo DB
            newProduct.setCategoryId(category.getCategoryId());
            newProduct.setLowStockThreshold(10);
            newProduct.setIsActive(true);

            boolean added = productDAO.insertProduct(newProduct);
            System.out.println("Thêm sản phẩm: " + (added ? "Thành công" : "Thất bại"));

            // 2. Lấy tất cả sản phẩm và in ra
            List<Product> products = productDAO.getAllProducts();
            System.out.println("Danh sách sản phẩm:");
            for (Product p : products) {
                System.out.printf("ID: %d, Mã: %s, Tên: %s, Category ID: %d\n",
                        p.getProductId(), p.getProductCode(), p.getProductName(), p.getCategoryId());
            }

            // 3. Lấy sản phẩm vừa thêm (theo mã)
            Product inserted = null;
            for (Product p : products) {
                if ("SP001".equals(p.getProductCode())) {
                    inserted = p;
                    break;
                }
            }

            if (inserted == null) {
                System.out.println("Không tìm thấy sản phẩm vừa thêm.");
                return;
            }

            // 4. Cập nhật sản phẩm
            inserted.setProductName("Sản phẩm đã cập nhật");
            boolean updated = productDAO.updateProduct(inserted);
            System.out.println("Cập nhật sản phẩm: " + (updated ? "Thành công" : "Thất bại"));

            // 5. Xóa sản phẩm
            boolean deleted = productDAO.deleteProduct(inserted.getProductId());
            System.out.println("Xóa sản phẩm: " + (deleted ? "Thành công" : "Thất bại"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
