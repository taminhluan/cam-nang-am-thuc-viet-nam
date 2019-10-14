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
    private Recipe mRecipe;

    public RecipeAdminDetailFragment() {
        // Required empty public constructor
    }

    public RecipeAdminDetailFragment(Recipe recipe) {
        mRecipe = recipe;
    }

    public static RecipeAdminDetailFragment newInstance() {
        return new RecipeAdminDetailFragment();
    }

    public static RecipeAdminDetailFragment newInstance(Recipe recipe) {
        return new RecipeAdminDetailFragment(recipe);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.recipe_admin_detail_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {


    }

    @Override
    public void display(Recipe recipe) {
        //TODO display recipe
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
