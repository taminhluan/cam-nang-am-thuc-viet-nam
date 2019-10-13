package com.example.recipes.admin.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.recipes.R;
import com.example.recipes.admin.AdminActivity;
import com.example.recipes.app.area.AreaFragment;
import com.example.recipes.util.ActivityUtils;

public class EventAdminActivity extends AdminActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_admin_act);

        setupToolbar();

        setupDrawer();

        setupFragment();
    }
    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.event);
        setSupportActionBar(toolbar);
    }

    private void setupFragment() {
        EventAdminFragment fragment =
                (EventAdminFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = EventAdminFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
