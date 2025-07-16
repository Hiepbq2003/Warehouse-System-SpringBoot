package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    boolean existsBySupplierName(String name);

    boolean existsByEmail(String email);

    Supplier findBySupplierName(String name);

    Supplier findByEmail(String email);

    @Query("SELECT s FROM Supplier s WHERE s.supplierName LIKE %:keyword% OR s.email LIKE %:keyword%")
    List<Supplier> searchByNameOrEmail(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM suppliers WHERE supplier_name LIKE CONCAT('%', :searchTerm, '%') LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Supplier> searchWithPaging(@Param("searchTerm") String searchTerm,
                                    @Param("offset") int offset,
                                    @Param("limit") int limit);

    @Query(value = "SELECT COUNT(*) FROM suppliers WHERE supplier_name LIKE CONCAT('%', :searchTerm, '%')", nativeQuery = true)
    int countFiltered(@Param("searchTerm") String searchTerm);
}
