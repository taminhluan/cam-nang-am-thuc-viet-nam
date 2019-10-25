package com.example.recipes.app.category;

import com.example.recipes.model.Category;

import java.util.List;

public interface ICategoryFragment {
    void onGetList();
    void onDisplayList(List<Category> categories);
    void goToRecipesByCategory(Category category);
}
