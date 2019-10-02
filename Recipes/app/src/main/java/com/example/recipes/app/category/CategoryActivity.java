package com.example.recipes.app.category;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.recipes.BaseActivity;
import com.example.recipes.R;
import com.example.recipes.util.ActivityUtils;

public class CategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_act);

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
        CategoryFragment fragment =
                (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = CategoryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
