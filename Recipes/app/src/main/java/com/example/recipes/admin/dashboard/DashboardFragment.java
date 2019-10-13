package com.example.recipes.admin.dashboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.admin.area.AreaAdminActivity;
import com.example.recipes.admin.category.CategoryAdminActivity;
import com.example.recipes.admin.event.EventAdminActivity;
import com.example.recipes.admin.recipe.RecipeAdminActivity;

public class DashboardFragment extends BaseFragment implements IDashboardFragment, View.OnClickListener {

    private Button mBtnAreaAdmin;
    private Button mBtnAreaCategory;
    private Button mBtnEventCategory;
    private Button mBtnRecipeCategory;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.dashboard_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {
        mBtnAreaAdmin = view.findViewById(R.id.btnAreaAdmin);
        mBtnAreaAdmin.setOnClickListener(this);

        mBtnAreaCategory = view.findViewById(R.id.btnCategoryAdmin);
        mBtnAreaCategory.setOnClickListener(this);

        mBtnEventCategory = view.findViewById(R.id.btnEventAdmin);
        mBtnEventCategory.setOnClickListener(this);

        mBtnRecipeCategory = view.findViewById(R.id.btnRecipeAdmin);
        mBtnRecipeCategory.setOnClickListener(this);
    }

    @Override
    public void goToAreaAdmin() {
        startActivity(new Intent(getActivityNonNull(), AreaAdminActivity.class));
    }

    @Override
    public void goToCategoryAdmin() {
        startActivity(new Intent(getActivityNonNull(), CategoryAdminActivity.class));
    }

    @Override
    public void goToEventAdmin() {
        startActivity(new Intent(getActivityNonNull(), EventAdminActivity.class));
    }

    @Override
    public void goToRecipeAdmin() {
        startActivity(new Intent(getActivityNonNull(), RecipeAdminActivity.class));
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAreaAdmin) {
            goToAreaAdmin();
        } else if (view == mBtnAreaCategory) {
            goToCategoryAdmin();
        } else if (view == mBtnEventCategory) {
            goToEventAdmin();
        } else if (view == mBtnRecipeCategory) {
            goToRecipeAdmin();
        }
    }
}
