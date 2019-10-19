package com.example.recipes.admin.event;

import com.example.recipes.model.Event;

import java.util.List;

public interface IEventAdminFragment {
    void getList();

    void goToAddEventFragment();

    void goToUpdateEventFragment(Event event);

    void displayList(List<Event> events);
}
