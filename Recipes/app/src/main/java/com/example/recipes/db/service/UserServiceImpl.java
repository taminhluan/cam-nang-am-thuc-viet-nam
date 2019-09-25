package com.example.recipes.db.service;

public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }
}
