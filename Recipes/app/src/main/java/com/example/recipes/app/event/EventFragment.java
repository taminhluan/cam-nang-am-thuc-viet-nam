package com.example.recipes.app.event;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.model.Event;

import java.util.List;

public class EventFragment extends Fragment implements IEventFragment {

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.event_frag, container, false);
    }

    @Override
    public void onGetList() {
        //TODO: on get list
    }

    @Override
    public void onDisplayList(List<Event> events) {
        //TODO: on display list
    }
}
