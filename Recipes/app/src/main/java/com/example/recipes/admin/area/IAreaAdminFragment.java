package com.example.recipes.admin.area;

import com.example.recipes.model.Area;

import java.util.List;

public interface IAreaAdminFragment {
    void getList();

    void goToAddAreaFragment();

    void goToUpdateAreaFragment(Area area);

    void displayList(List<Area> areas);
}
