package com.example.recipes.admin.area;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.recipes.R;
import com.example.recipes.admin.AdminActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.model.Area;
import com.example.recipes.util.ActivityUtils;

import java.io.Serializable;

public class AreaAdminDetailActivity extends AdminActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_admin_detail_act);

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
        AreaAdminDetailFragment fragment =
                (AreaAdminDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            if (getIntent().hasExtra(AppConstant.AREA)) {
                 Area area = (Area) getIntent().getSerializableExtra(AppConstant.AREA);
                fragment = AreaAdminDetailFragment.newInstance(area);
            } else {
                fragment = AreaAdminDetailFragment.newInstance();
            }
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
