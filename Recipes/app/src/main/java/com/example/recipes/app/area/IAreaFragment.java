package com.example.recipes.app.area;

import com.example.recipes.model.Area;

import java.util.List;

public interface IAreaFragment {
    void onGetList();
    void onDisplayList(List<Area> areas);
}
