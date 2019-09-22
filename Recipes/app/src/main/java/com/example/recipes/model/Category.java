package com.example.recipes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String image;
}
