package com.example.recipes.admin.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.recipes.R;
import com.example.recipes.admin.AdminActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.model.Event;
import com.example.recipes.util.ActivityUtils;

public class EventAdminDetailActivity extends AdminActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_admin_detail_act);

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
        EventAdminDetailFragment fragment =
                (EventAdminDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            if (getIntent().hasExtra(AppConstant.EVENT)) {
                Event event = (Event) getIntent().getSerializableExtra(AppConstant.EVENT);
                fragment = EventAdminDetailFragment.newInstance(event);
            } else {
                fragment = EventAdminDetailFragment.newInstance();
            }
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
