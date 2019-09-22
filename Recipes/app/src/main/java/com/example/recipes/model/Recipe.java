package com.example.recipes.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Area.class,
            parentColumns = "uid",
            childColumns = "areaId"),
        @ForeignKey(entity = Category.class,
                parentColumns = "uid",
                childColumns = "categoryId"),
        @ForeignKey(entity = Event.class,
                parentColumns = "uid",
                childColumns = "eventId"),
})
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String image;

    @ColumnInfo
    public String detail;

    @ColumnInfo
    public String ingredients;

    public int areaId;

    public int eventId;

    public int categoryId;
}
