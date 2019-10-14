package com.example.recipes.admin.recipe;

import com.example.recipes.model.Recipe;

import java.util.List;

public interface IRecipeAdminFragment {
    void getList();
    void goToAddRecipeFragment();
    void displayList(List<Recipe> recipes);
}
