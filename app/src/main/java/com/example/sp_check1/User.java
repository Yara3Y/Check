package com.example.sp_check1;

public class User {
    private static User instance;

    private User() {
        // Private constructor to prevent instantiation from outside the class
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }


}