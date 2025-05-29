package dao;

import model.Product;
import model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm sản phẩm, trả về id vừa tạo hoặc -1 nếu lỗi
    public int insert(Product product) throws SQLException {
        String sql = "INSERT INTO products (product_code, product_name, description, unit, purchase_price, sale_price, supplier_id, category_id, low_stock_threshold, is_active, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getUnit());
            stmt.setDouble(5, product.getPurchasePrice());
            stmt.setDouble(6, product.getSalePrice());
            if (product.getSupplierId() != 0) {
                stmt.setInt(7, product.getSupplierId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            if (product.getCategoryId() != 0) {
                stmt.setInt(8, product.getCategoryId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            stmt.setInt(9, product.getLowStockThreshold());
            stmt.setBoolean(10, product.getIsActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return -1;

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        }
    }

    // Lấy sản phẩm theo ID
    public Product findById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
                return null;
            }
        }
    }


    // Cập nhật sản phẩm
    public boolean update(Product product) throws SQLException {
        String sql = "UPDATE products SET product_code = ?, product_name = ?, description = ?, unit = ?, purchase_price = ?, sale_price = ?, supplier_id = ?, category_id = ?, low_stock_threshold = ?, is_active = ?, updated_at = CURRENT_TIMESTAMP WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getUnit());
            stmt.setDouble(5, product.getPurchasePrice());
            stmt.setDouble(6, product.getSalePrice());
            if (product.getSupplierId() != 0) {
                stmt.setInt(7, product.getSupplierId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            if (product.getCategoryId() != 0) {
                stmt.setInt(8, product.getCategoryId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            stmt.setInt(9, product.getLowStockThreshold());
            stmt.setBoolean(10, product.getIsActive());
            stmt.setInt(11, product.getProductId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa sản phẩm
    public boolean delete(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Map ResultSet thành Product
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setProductCode(rs.getString("product_code"));
        p.setProductName(rs.getString("product_name"));
        p.setDescription(rs.getString("description"));
        p.setUnit(rs.getString("unit"));
        p.setPurchasePrice(rs.getDouble("purchase_price"));
        p.setSalePrice(rs.getDouble("sale_price"));
        int supplierId = rs.getInt("supplier_id");
        if (!rs.wasNull()) p.setSupplierId(supplierId);
        int categoryId = rs.getInt("category_id");
        if (!rs.wasNull()) p.setCategoryId(categoryId);
        p.setLowStockThreshold(rs.getInt("low_stock_threshold"));
        p.setIsActive(rs.getBoolean("is_active"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setUpdatedAt(rs.getTimestamp("updated_at"));
        return p;
    }

    // Lấy danh sách nhà cung cấp
    public List<Supplier> findAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT supplier_id, supplier_name, address, phone FROM suppliers";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplier_id"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setAddress(rs.getString("address"));
                s.setPhoneNumber(rs.getString("phone"));
                suppliers.add(s);
            }
        }
        return suppliers;
    }
     public List<Supplier> findAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT supplier_id, supplier_name, address, phone FROM suppliers";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplier_id"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setAddress(rs.getString("address"));
                s.setPhoneNumber(rs.getString("phone"));
                list.add(s);
            }
        }
        return list;
    }
     public List<Product> findProducts(String search, Integer supplierId, Boolean isActive,
                                 Float minPurchasePrice, Float minSalePrice,
                                 int page, int pageSize) throws SQLException {
    List<Product> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1 ");

    if (search != null && !search.isEmpty()) {
        sql.append(" AND product_name LIKE ? ");
    }
    if (supplierId != null) {
        sql.append(" AND supplier_id = ? ");
    }
    if (isActive != null) {
        sql.append(" AND is_active = ? ");
    }
    if (minPurchasePrice != null) {
        sql.append(" AND purchase_price >= ? ");
    }
    if (minSalePrice != null) {
        sql.append(" AND sale_price >= ? ");
    }

    sql.append(" ORDER BY product_id LIMIT ? OFFSET ?");

    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int index = 1;
        if (search != null && !search.isEmpty()) {
            ps.setString(index++, "%" + search + "%");
        }
        if (supplierId != null) {
            ps.setInt(index++, supplierId);
        }
        if (isActive != null) {
            ps.setBoolean(index++, isActive);
        }
        if (minPurchasePrice != null) {
            ps.setFloat(index++, minPurchasePrice);
        }
        if (minSalePrice != null) {
            ps.setFloat(index++, minSalePrice);
        }
        ps.setInt(index++, pageSize);
        ps.setInt(index, (page - 1) * pageSize);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product();
                // set các thuộc tính p từ rs
                // vd:
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                // ... các trường khác
                list.add(p);
            }
        }
    }
    return list;
}
public int getTotalFilteredProducts(String search, Integer supplierId, Boolean isActive,
                                    Float minPurchasePrice, Float minSalePrice) throws SQLException {
    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products WHERE 1=1 ");

    if (search != null && !search.isEmpty()) {
        sql.append(" AND product_name LIKE ? ");
    }
    if (supplierId != null) {
        sql.append(" AND supplier_id = ? ");
    }
    if (isActive != null) {
        sql.append(" AND is_active = ? ");
    }
    if (minPurchasePrice != null) {
        sql.append(" AND purchase_price >= ? ");
    }
    if (minSalePrice != null) {
        sql.append(" AND sale_price >= ? ");
    }

    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int index = 1;
        if (search != null && !search.isEmpty()) {
            ps.setString(index++, "%" + search + "%");
        }
        if (supplierId != null) {
            ps.setInt(index++, supplierId);
        }
        if (isActive != null) {
            ps.setBoolean(index++, isActive);
        }
        if (minPurchasePrice != null) {
            ps.setFloat(index++, minPurchasePrice);
        }
        if (minSalePrice != null) {
            ps.setFloat(index++, minSalePrice);
        }

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }
    return 0;
}

}
