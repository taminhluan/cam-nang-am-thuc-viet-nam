package com.example.recipes.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipes.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe WHERE uid IN (:recipeIds)")
    List<Recipe> loadAllByIds(int[] recipeIds);

    @Query("SELECT * FROM recipe WHERE name LIKE :name LIMIT 1")
    Recipe findByName(String name);

    @Insert
    void insertAll(Recipe... recipes);

    @Update
    void updateAll(Recipe... recipes);

    @Delete
    void delete(Recipe recipe);
}
