package com.example.recipes.admin.event;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;

public class EventAdminFragment extends BaseFragment {


    public EventAdminFragment() {
        // Required empty public constructor
    }

    public static EventAdminFragment newInstance() {
        return new EventAdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.event_admin_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {
    }

}
