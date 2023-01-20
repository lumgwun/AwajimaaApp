package com.skylightapp.Classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHelpers {
    public static String passwordHash(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));

        } catch (Exception e) {
            return null;
        }
    }

    public static boolean comparePassword(String pw1, String pw2) {
        return pw1.equals(passwordHash(pw2));
    }
    public static String md5(String s) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
