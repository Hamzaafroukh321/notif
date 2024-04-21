package com.example.managementsystem.config;

public class PasswordGenerator {

    public static String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder("DXC-");

        for (int i = 0; i < 12; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
