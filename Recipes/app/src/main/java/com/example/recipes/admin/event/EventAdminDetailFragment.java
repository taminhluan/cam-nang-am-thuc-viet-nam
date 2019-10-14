package com.example.recipes.admin.event;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.model.Event;

public class EventAdminDetailFragment extends BaseFragment implements IEventAdminDetailFragment {
    private Event mEvent;

    public EventAdminDetailFragment() {
        // Required empty public constructor
    }

    public EventAdminDetailFragment(Event event) {
        mEvent = event;
    }

    public static EventAdminDetailFragment newInstance() {
        return new EventAdminDetailFragment();
    }

    public static EventAdminDetailFragment newInstance(Event event) {
        return new EventAdminDetailFragment(event);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.event_admin_detail_frag, container, false);
        mapping(inflate);
        return inflate;
    }

    private void mapping(View view) {

    }

    @Override
    public void display(Event event) {
        if (event == null) return;

        //TODO display
    }

    @Override
    public void add(Event event) {
        //TODO add

        back();
    }

    @Override
    public void update(Event event) {
        //TODO update

        back();
    }

    @Override
    public void delete(Event event) {
        //TODO delete

        back();
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }
}
