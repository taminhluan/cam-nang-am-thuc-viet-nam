package com.example.recipes.admin.area;

import com.example.recipes.model.Area;
public interface IAreaAdminDetailFragment {
    void display(Area area);
    void add(Area area);
    void update(Area area);
    void delete(Area area);
    void back();
}
