package com.example.recipes.admin.dashboard;

import android.os.Bundle;

import com.example.recipes.admin.AdminActivity;
import com.example.recipes.app.area.AreaFragment;
import com.example.recipes.util.ActivityUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.recipes.R;

public class DashboardActivity extends AdminActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_act);
        setupToolbar();

        setupDrawer();

        setupFragment();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.admin);
        setSupportActionBar(toolbar);
    }

    private void setupFragment() {
        DashboardFragment fragment =
                (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = DashboardFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }

}
