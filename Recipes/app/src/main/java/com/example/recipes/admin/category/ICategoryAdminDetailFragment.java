package com.example.recipes.admin.category;

import com.example.recipes.model.Category;

public interface ICategoryAdminDetailFragment {
    void display(Category category);
    void add(Category category);
    void update(Category category);
    void delete(Category category);
    void back();

}
