package com.example.recipes.app.recipe;

import com.example.recipes.model.Recipe;

import java.util.List;

public interface IRecipeFragment {
    void onGetList();
    void onDisplayList(List<Recipe> recipes);
    void goToRecipeDetail(Recipe recipe);
}
