package com.example.recipes.app.main;

import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;

import java.util.List;

public interface IMainFragment {
    void onGetAreas();
    void onGetCategories();
    void onGetEvents();
    void onGetRecipes();

    void onLoadMoreAreas();
    void onLoadMoreCategories();
    void onLoadMoreEvents();
    void onLoadMoreRecipes();

    void onDisplayRecipes(List<Recipe> recipes);
    void onDisplayCategories(List<Category> categories);
    void onDisplayEvents(List<Event> events);
    void onDisplayAreas(List<Area> areas);
}
