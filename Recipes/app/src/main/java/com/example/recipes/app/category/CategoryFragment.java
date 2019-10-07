package com.example.recipes.app.category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.recipes.R;
import com.example.recipes.app.recipe.RecipeActivity;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Recipe;

import java.util.List;

public class CategoryFragment extends Fragment implements ICategoryFragment, View.OnClickListener {
    private ImageView mFull;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.category_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {
        mFull = view.findViewById(R.id.full);
        mFull.setOnClickListener(this);
    }

    @Override
    public void onGetList() {
        //TODO on get list
    }

    @Override
    public void onDisplayList(List<Category> categories) {
        //TODO on display list
    }

    @Override
    public void onClick(View view) {
        if (view == mFull) {
            Intent intent = new Intent(getActivity(), RecipeActivity.class);
            startActivity(intent);
        }
    }
}
