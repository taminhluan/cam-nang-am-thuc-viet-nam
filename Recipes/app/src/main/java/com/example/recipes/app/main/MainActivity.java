package com.example.recipes.app.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.recipes.BaseActivity;
import com.example.recipes.R;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetListAsyncTask().execute();
    }

    public class GetListAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "recipe_db").build();
            List<Area> all = db.getAreaDao().getAll();
            String recipe_db = getApplicationContext().getDatabasePath("recipe_db").getAbsolutePath();
            return null;
        }
    }
}
