package com.example.recipes.admin.event;

import com.example.recipes.model.Event;

public interface IEventAdminDetailFragment {

    void display(Event event);
    void add(Event event);
    void update(Event event);
    void delete(Event event);

    void openCamera();

    void back();
}
