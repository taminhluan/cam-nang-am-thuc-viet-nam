package com.example.recipes.admin.recipe;

import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;

import java.util.List;

public interface IRecipeAdminDetailFragment {
    void display(Recipe recipe);
    void add(Recipe recipe);
    void update(Recipe recipe);
    void delete(Recipe recipe);

    void getListAreas();
    void getListCategories();
    void getListEvents();

    void displayListAreas(List<Area> areas);
    void displayListCategories(List<Category> categories);
    void displayListEvents(List<Event> events);

    void back();
}
