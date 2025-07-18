package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.ActivityLog;
import org.clotheswarehouse_hsf.model.Role;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.repository.UserRepository;
import org.clotheswarehouse_hsf.service.ActivityLogService;
import org.clotheswarehouse_hsf.service.UserService;
import org.clotheswarehouse_hsf.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    @Transactional
    public User save(User user) {
        Timestamp now = Timestamp.from(Instant.now());
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(now);
        }
        user.setUpdatedAt(now);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean update(User user) {
        if (user == null || user.getUserId() == null) return false;
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean updatePassword(Integer id, String newPasswordHash) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) return false;

        User user = opt.get();
        user.setPasswordHash(newPasswordHash);
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean addUser(User user, String rawPassword) throws NoSuchAlgorithmException {
        if (user == null || rawPassword == null || rawPassword.isEmpty()) return false;
        user.setPasswordHash(passwordUtil.hashPassword(rawPassword));
        Timestamp now = Timestamp.from(Instant.now());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUser(User updatedUser, boolean resetPassword) throws NoSuchAlgorithmException {
        Optional<User> opt = userRepository.findById(updatedUser.getUserId());
        if (opt.isEmpty()) return false;

        User user = opt.get();
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setRole(updatedUser.getRole());
        user.setActive(updatedUser.isActive());
        user.setUpdatedAt(Timestamp.from(Instant.now()));

        if (resetPassword) {
            user.setPasswordHash(passwordUtil.hashPassword("123"));
        }

        userRepository.save(user);
        return true;
    }

    @Override
    public boolean inactiveUser(Integer id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) return false;

        User user = opt.get();
        user.setActive(false);
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> filterUsers(String keyword, String sort) {
        List<User> users;

        if (keyword != null && !keyword.isBlank()) {
            users = userRepository.findByUsernameContainingIgnoreCase(keyword);
        } else {
            users = userRepository.findAll();
        }

        Comparator<User> comparator = null;

        if ("username_asc".equals(sort)) {
            comparator = Comparator.comparing(User::getUsername, String.CASE_INSENSITIVE_ORDER);
        } else if ("username_desc".equals(sort)) {
            comparator = Comparator.comparing(User::getUsername, String.CASE_INSENSITIVE_ORDER).reversed();
        } else if ("created_asc".equals(sort)) {
            comparator = Comparator.comparing(User::getCreatedAt);
        } else if ("created_desc".equals(sort)) {
            comparator = Comparator.comparing(User::getCreatedAt).reversed();
        }

        if (comparator != null) {
            users.sort(comparator);
        }

        return users;
    }

    @Override
    public List<User> getAllSalesStaff() {
        return userRepository.findByRole(Role.fromString("sales_staff"));

    }
    @Override
    public List<User> getAllPurchasingStaff() {
        return userRepository.findByRole(Role.fromString("purchasing_staff"));
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
