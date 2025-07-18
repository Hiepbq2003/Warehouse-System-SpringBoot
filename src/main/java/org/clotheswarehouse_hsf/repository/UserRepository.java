package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.Role;
import org.clotheswarehouse_hsf.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = "role")
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = "role")
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "role")
    List<User> findByUsernameContainingIgnoreCase(String keyword);

    @EntityGraph(attributePaths = "role")
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "role")
    Page<User> findByUsernameContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByRole_RoleName(String roleName);

}
