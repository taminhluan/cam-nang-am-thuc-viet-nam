package com.example.recipes.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipes.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE uid IN (:categoryIds)")
    List<Category> loadAllByIds(int[] categoryIds);

    @Query("SELECT * FROM category WHERE name LIKE :name LIMIT 1")
    Category findByName(String name);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
