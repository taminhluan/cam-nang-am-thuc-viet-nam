package com.example.recipes.app.recipe_detail;

import com.example.recipes.model.Recipe;

public interface IRecipeDetailFragment {
    void onGetData(int id);
    void onDisplayData(Recipe recipe);
    void onShareOnFacebook();

    void onGoToArea();
    void onGoToEvent();
    void onGoToCategory();
}
