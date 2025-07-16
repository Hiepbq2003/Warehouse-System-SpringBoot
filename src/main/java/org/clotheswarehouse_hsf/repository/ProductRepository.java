package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    boolean existsByProductName(String productName);

    List<Product> findByIsActive(Boolean isActive);

    boolean existsByProductCode(String productCode);

    List<Product> findByCategory_Id(Integer categoryId);

    List<Product> findByProductCodeIn(List<String> codes);

    List<Product> findByIsActiveTrue();

    @Query(value = """
            SELECT * FROM products
            WHERE (product_code LIKE %:searchTerm% OR product_name LIKE %:searchTerm%)
            AND (:active IS NULL OR is_active = :active)
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Product> searchFilterPaging(
            @Param("searchTerm") String searchTerm,
            @Param("active") Boolean active,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """
            SELECT COUNT(*) FROM products
            WHERE (product_code LIKE %:searchTerm% OR product_name LIKE %:searchTerm%)
            AND (:active IS NULL OR is_active = :active)
            """, nativeQuery = true)
    int countSearchFilter(
            @Param("searchTerm") String searchTerm,
            @Param("active") Boolean active
    );

}
