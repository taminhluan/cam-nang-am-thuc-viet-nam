package com.example.recipes.app.main;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.recipes.BaseActivity;
import com.example.recipes.R;
import com.example.recipes.app.drawer.DrawerFragment;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.util.ActivityUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);

        setupToolbar();

        setupDrawer();

        setupFragment();

        new GetListAsyncTask().execute();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void setupFragment() {
        MainFragment aboutFragment =
                (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (aboutFragment == null) {
            // Create the fragment
            aboutFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), aboutFragment, R.id.contentFrame);
        }
    }

    private void setupDrawer() {
        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUpDrawer(R.id.fragment_navigation_drawer, drawerLayout, (Toolbar) findViewById(R.id.toolbar));
    }

    public class GetListAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(MainActivity.this);
            return null;
        }
    }
}
