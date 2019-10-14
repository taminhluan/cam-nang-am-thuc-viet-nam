package com.example.recipes.admin.recipe;

import com.example.recipes.model.Recipe;

public interface IRecipeAdminDetailFragment {
    void display(Recipe recipe);
    void add(Recipe recipe);
    void update(Recipe recipe);
    void delete(Recipe recipe);
    void back();
}
