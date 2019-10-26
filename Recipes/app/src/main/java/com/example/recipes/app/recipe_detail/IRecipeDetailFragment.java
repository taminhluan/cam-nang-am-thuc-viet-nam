package com.example.recipes.app.recipe_detail;

import com.example.recipes.model.Recipe;

public interface IRecipeDetailFragment {
    void getDataFromIntent();
    void onDisplayData(Recipe recipe);
    void onShareOnFacebook();

    void onDisplayMoreInfo(Recipe recipe);

    void onGoToArea();
    void onGoToEvent();
    void onGoToCategory();
}
