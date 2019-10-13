package com.example.recipes.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.recipes.BaseActivity;
import com.example.recipes.app.login.LoginActivity;
import com.example.recipes.util.Global;

public class AdminActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Global.Logged) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
