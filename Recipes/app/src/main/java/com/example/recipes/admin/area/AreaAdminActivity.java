package com.example.recipes.admin.area;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.recipes.R;
import com.example.recipes.admin.AdminActivity;
import com.example.recipes.util.ActivityUtils;

public class AreaAdminActivity extends AdminActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_admin_act);

        setupToolbar();

        setupDrawer();

        setupFragment();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.areas);
        setSupportActionBar(toolbar);
    }

    private void setupFragment() {
        AreaAdminFragment fragment =
                (AreaAdminFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = AreaAdminFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
