package com.example.recipes.admin.recipe;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.model.Recipe;

public class RecipeAdminDetailFragment extends BaseFragment implements IRecipeAdminDetailFragment {


    public RecipeAdminDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipe_admin_detail_frag, container, false);
    }

    @Override
    public void add(Recipe recipe) {
        //TODO add

        back();
    }

    @Override
    public void update(Recipe recipe) {
        //TODO: update

        back();
    }

    @Override
    public void delete(Recipe recipe) {
        //TODO: delete

        back();
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }
}
