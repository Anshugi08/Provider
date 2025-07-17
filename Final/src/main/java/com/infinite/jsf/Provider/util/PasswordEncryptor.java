package com.infinite.jsf.Provider.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordEncryptor {

    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Hashes the given plaintext password using SHA-256 and returns
     * a lowercase hex string.
     *
     * @param password the raw password
     * @return SHA-256 hex digest
     * @throws RuntimeException if the algorithm isn’t available
     */
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] digest = md.digest(bytes);
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            // This should never happen if SHA-256 is supported
            throw new RuntimeException("Password hashing algorithm not available", e);
        }
    }

    /**
     * Converts a byte array into its lowercase hex representation.
     */
    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >>> 4) & 0xF, 16))
              .append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }

    /**
     * Verifies a plaintext password against a stored hash.
     *
     * @param password      the raw password attempt
     * @param storedHashHex the previously stored SHA-256 hex string
     * @return true if match, false otherwise
     */
    public static boolean verify(String password, String storedHashHex) {
        return encrypt(password).equals(storedHashHex);
    }
}
