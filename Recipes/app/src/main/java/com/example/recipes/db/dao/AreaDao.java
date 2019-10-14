package com.example.recipes.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import com.example.recipes.model.Area;

@Dao
public interface AreaDao {
    @Query("SELECT * FROM area")
    List<Area> getAll();

    @Query("SELECT * FROM area WHERE uid IN (:areaIds)")
    List<Area> loadAllByIds(int[] areaIds);

    @Query("SELECT * FROM area WHERE name LIKE :name LIMIT 1")
    Area findByName(String name);

    @Insert
    void insertAll(Area... areas);

    @Update
    void updateAll(Area... areas);

    @Delete
    void delete(Area area);
}
