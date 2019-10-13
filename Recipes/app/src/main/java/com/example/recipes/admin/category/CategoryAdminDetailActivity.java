package com.example.recipes.admin.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.recipes.R;
import com.example.recipes.admin.AdminActivity;
import com.example.recipes.util.ActivityUtils;

public class CategoryAdminDetailActivity extends AdminActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_admin_detail_act);

        setupToolbar();

        setupDrawer();

        setupFragment();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.category);
        setSupportActionBar(toolbar);
    }

    private void setupFragment() {
        CategoryAdminFragment fragment =
                (CategoryAdminFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = CategoryAdminFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
