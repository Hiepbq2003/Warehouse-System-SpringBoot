package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    User findById(Integer id);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();

    Page<User> searchUsers(String keyword, Pageable pageable);

    User save(User user);

    boolean update(User user);

    boolean updatePassword(Integer id, String newPasswordHash);

    void deleteById(Integer id);

    boolean addUser(User user, String rawPassword) throws NoSuchAlgorithmException;

    boolean updateUser(User updatedUser, boolean resetPassword) throws NoSuchAlgorithmException;

    boolean inactiveUser(Integer id);

    List<User> filterUsers(String keyword, String sort);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> getAllSalesStaff();

    List<User> getAllPurchasingStaff();

    long count();
}
