package com.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordGenerator {

    public static String generatePassword(String domain, String SALT) {
        try {
            String input = domain + SALT;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));


            String password = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);


            int desiredLength = 12;
            if (password.length() > desiredLength) {
                password = password.substring(0, desiredLength);
            } else if (password.length() < desiredLength) {
                while (password.length() < desiredLength) {
                    password += "A"; // Padding character, change if needed
                }
            }

            return password;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
