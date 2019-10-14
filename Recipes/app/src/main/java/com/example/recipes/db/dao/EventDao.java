package com.example.recipes.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipes.model.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE uid IN (:eventIds)")
    List<Event> loadAllByIds(int[] eventIds);

    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    Event findByName(String name);

    @Insert
    void insertAll(Event... events);

    @Update
    void updateAll(Event... events);

    @Delete
    void delete(Event event);
}
