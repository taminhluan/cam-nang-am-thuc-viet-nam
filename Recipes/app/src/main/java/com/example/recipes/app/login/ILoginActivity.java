package com.example.recipes.app.login;

public interface ILoginActivity {
    void onLoginSuccess();
    void onLoginFailed();
    void onLogin(String username, String password);
    void onBack();
}
