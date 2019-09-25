package com.example.recipes.app.area;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.model.Area;

import java.util.List;

public class AreaFragment extends Fragment implements IAreaFragment {


    public AreaFragment() {
        // Required empty public constructor
    }

    public static AreaFragment newInstance() {
        return new AreaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.area_frag, container, false);
    }

    @Override
    public void onGetList() {
        //TODO: on Get List
    }

    @Override
    public void onDisplayList(List<Area> areas) {
        //TODO: on display list
    }
}
