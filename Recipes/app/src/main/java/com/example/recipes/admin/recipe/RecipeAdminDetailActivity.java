package com.example.recipes.admin.recipe;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.recipes.R;
import com.example.recipes.admin.AdminActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.model.Recipe;
import com.example.recipes.util.ActivityUtils;

public class RecipeAdminDetailActivity extends AdminActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_admin_detail_act);

        setupToolbar();

        setupDrawer();

        setupFragment();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.recipe);
        setSupportActionBar(toolbar);
    }

    private void setupFragment() {
        RecipeAdminDetailFragment fragment =
                (RecipeAdminDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            if (getIntent().hasExtra(AppConstant.RECIPE)) {
                Recipe recipe = (Recipe) getIntent().getSerializableExtra(AppConstant.RECIPE);
                fragment = RecipeAdminDetailFragment.newInstance(recipe);
            } else {
                fragment = RecipeAdminDetailFragment.newInstance();
            }
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
