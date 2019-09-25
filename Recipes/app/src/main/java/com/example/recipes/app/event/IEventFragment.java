package com.example.recipes.app.event;

import com.example.recipes.model.Event;

import java.util.List;

public interface IEventFragment {
    void onGetList();
    void onDisplayList(List<Event> events);
}
