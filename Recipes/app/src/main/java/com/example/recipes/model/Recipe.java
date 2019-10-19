package com.example.recipes.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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
public class Recipe implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String image;

    @ColumnInfo
    private String detail;

    @ColumnInfo
    private String ingredients;

    private int areaId;

    private int eventId;

    private int categoryId;

    //fields obtain by code
    @Ignore
    private Area area;

    @Ignore
    private Event event;

    @Ignore
    private Category category;
    //end fields obtain by code

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
