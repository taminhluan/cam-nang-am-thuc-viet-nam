package com.example.recipes.admin.area;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.model.Area;

public class AreaAdminDetailFragment extends BaseFragment implements IAreaAdminDetailFragment {
    private Area mArea;

    public AreaAdminDetailFragment() {
        // Required empty public constructor
    }

    public AreaAdminDetailFragment(Area area) {
        mArea = area;
    }

    public static AreaAdminDetailFragment newInstance() {
        return new AreaAdminDetailFragment();
    }

    public static AreaAdminDetailFragment newInstance(Area area) {
        return new AreaAdminDetailFragment(area);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.area_admin_detail_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {

    }

    @Override
    public void display(Area area) {
        if (area == null) return;
    }

    @Override
    public void add(Area area) {
        //TODO add

        back();
    }

    @Override
    public void update(Area area) {
        //TODO update
        back();
    }

    @Override
    public void delete(Area area) {
        //TODO delete

        back();
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }
}
