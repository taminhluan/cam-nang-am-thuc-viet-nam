package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.recipes.app.drawer.DrawerFragment;

public class BaseActivity extends AppCompatActivity {
    protected void setupDrawer() {
        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUpDrawer(R.id.fragment_navigation_drawer, drawerLayout, (Toolbar) findViewById(R.id.toolbar));
    }
}
