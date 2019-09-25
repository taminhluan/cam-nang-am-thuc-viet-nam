package com.example.recipes.admin.category;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;

public class CategoryAdminFragment extends Fragment {


    public CategoryAdminFragment() {
        // Required empty public constructor
    }

    public static CategoryAdminFragment newInstant() {
        return new CategoryAdminFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.category_admin_frag, container, false);
    }

}
