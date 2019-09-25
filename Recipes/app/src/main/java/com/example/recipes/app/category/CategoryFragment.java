package com.example.recipes.app.category;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;

import java.util.List;

public class CategoryFragment extends Fragment implements ICategoryFragment {

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
        return inflater.inflate(R.layout.category_frag, container, false);
    }

    @Override
    public void onGetList() {
        //TODO on get list
    }

    @Override
    public void onDisplayList(List<Category> categories) {
        //TODO on display list
    }
}
