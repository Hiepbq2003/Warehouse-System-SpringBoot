package org.clotheswarehouse_hsf.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    public String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashPassword(String password) {
        String salt = generateSalt();
        String hash = hashWithSalt(password, salt);
        return salt + ":" + hash;
    }

    public boolean verifyPassword(String password, String storedHash) {
        if (password == null || storedHash == null) return false;

        String[] parts = storedHash.split(":");
        if (parts.length != 2) return false;

        String salt = parts[0];
        String expectedHash = parts[1];
        String actualHash = hashWithSalt(password, salt);

        return expectedHash.equals(actualHash);
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 2) return false;

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
//            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasDigit;
    }

    private String hashWithSalt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Thuật toán hash không hỗ trợ: " + ALGORITHM, e);
        }
    }
}
