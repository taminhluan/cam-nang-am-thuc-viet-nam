package com.example.recipes.admin.recipe;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;

public class RecipeAdminFragment extends BaseFragment {


    public RecipeAdminFragment() {
        // Required empty public constructor
    }

    public static RecipeAdminFragment newInstance() {
        return new RecipeAdminFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.recipe_admin_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {

    }

}
