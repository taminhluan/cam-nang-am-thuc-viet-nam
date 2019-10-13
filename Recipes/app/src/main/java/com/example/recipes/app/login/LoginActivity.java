package com.example.recipes.app.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipes.BaseActivity;
import com.example.recipes.R;
import com.example.recipes.admin.dashboard.DashboardActivity;
import com.example.recipes.db.service.UserService;
import com.example.recipes.db.service.UserServiceImpl;
import com.example.recipes.util.Global;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginActivity {

    private final UserService mUserService;

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnBack;

    public LoginActivity() {
        this.mUserService = new UserServiceImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        mapping();
    }

    private void mapping() {
        mEtUsername = findViewById(R.id.etUsername);
        mEtPassword = findViewById(R.id.etPassword);

        mBtnBack = findViewById(R.id.btnBack);
        mBtnBack.setOnClickListener(this);

        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnLogin) {
            onLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
        } else if (view == mBtnBack) {
            onBack();
        }
    }

    @Override
    public void onLoginSuccess() {
        Global.Logged = true;
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed() {
        //TODO: replace by label
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogin(String username, String password) {
        new LoginAsyncTask().execute(username, password);
    }

    @Override
    public void onBack() {
        finish();
    }

    public class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            if (params.length < 2) {
                throw new IllegalArgumentException("Have to 2 params username, password");
            }

            String username = params[0];
            String password = params[1];

            return mUserService.login(username, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                onLoginSuccess();
            } else {
                onLoginFailed();
            }
        }
    }
}
