package com.example.recipes.app.recipe;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.app.recipe_detail.RecipeDetailActivity;
import com.example.recipes.model.Recipe;

import java.util.List;

public class RecipeFragment extends BaseFragment implements IRecipeFragment, View.OnClickListener {
    private ImageView mFull;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.recipe_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {
        mFull = view.findViewById(R.id.full);
        mFull.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mFull) {
            Intent intent = new Intent(getActivityNonNull(), RecipeDetailActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onGetList() {

    }

    @Override
    public void onDisplayList(List<Recipe> recipes) {

    }
}
