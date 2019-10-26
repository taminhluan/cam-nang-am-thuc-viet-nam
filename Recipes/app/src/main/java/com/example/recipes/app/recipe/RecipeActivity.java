package com.example.recipes.app.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipes.BaseActivity;
import com.example.recipes.R;
import com.example.recipes.app.recipe_detail.RecipeDetailFragment;
import com.example.recipes.util.ActivityUtils;

public class RecipeActivity extends BaseActivity {
    private RecipeFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_act);

        setupToolbar();

        setupDrawer();

        setupFragment();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.recipe);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipe_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_filter:
                IRecipeFragment iRecipeFragment = mFragment;
                iRecipeFragment.openPopupFilter();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupFragment() {
        mFragment =
                (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mFragment == null) {
            // Create the fragment
            mFragment = RecipeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mFragment, R.id.contentFrame);
        }
    }
}
