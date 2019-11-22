package com.example.recipes.app.bookmark;

import com.example.recipes.model.Recipe;

import java.util.List;

public interface IBookmarkFragment {
    void onGetList();
    void onDisplayList(List<Recipe> recipes);
    void goToRecipeDetail(Recipe recipe);
}
