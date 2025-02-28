package com.example.recipes.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.dao.AreaDao;
import com.example.recipes.db.dao.CategoryDao;
import com.example.recipes.db.dao.EventDao;
import com.example.recipes.db.dao.RecipeDao;
import com.example.recipes.model.Category;
import com.example.recipes.model.Area;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;

@Database(entities = {Category.class, Area.class, Event.class, Recipe.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AreaDao getAreaDao();
    public abstract CategoryDao getCategoryDao();
    public abstract EventDao getEventDao();
    public abstract RecipeDao getRecipeDao();

    private static AppDatabase db = null;

    public static AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppConstant.DATABASE_NAME).build();
        }
        return db;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
