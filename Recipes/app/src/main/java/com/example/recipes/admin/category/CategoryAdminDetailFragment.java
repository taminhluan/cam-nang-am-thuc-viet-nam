package com.example.recipes.admin.category;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.model.Category;

public class CategoryAdminDetailFragment extends BaseFragment implements ICategoryAdminDetailFragment {
    private Category mCategory;

    public CategoryAdminDetailFragment() {
        // Required empty public constructor
    }

    public CategoryAdminDetailFragment(Category category) {
        mCategory = category;
    }

    public static CategoryAdminDetailFragment newInstance() {
        return new CategoryAdminDetailFragment();
    }

    public static CategoryAdminDetailFragment newInstance(Category category) {
        return new CategoryAdminDetailFragment(category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.category_admin_detail_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {
    }

    @Override
    public void display(Category category) {
        if (category == null) return;

        //TODO display
    }

    @Override
    public void add(Category category) {
        //TODO add category
        back();
    }

    @Override
    public void update(Category category) {
        //TODO update category
        back();
    }

    @Override
    public void delete(Category category) {
        //TODO delete category

        back();
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }
}
