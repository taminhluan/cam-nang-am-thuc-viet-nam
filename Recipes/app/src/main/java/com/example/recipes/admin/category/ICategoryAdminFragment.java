package com.example.recipes.admin.category;

import com.example.recipes.model.Category;

import java.util.List;

public interface ICategoryAdminFragment {
    void goToAddCategoryFragment();

    void displayList(List<Category> categories);
}
