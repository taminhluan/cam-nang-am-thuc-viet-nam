package com.example.recipes.app.main;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.app.area.AreaActivity;
import com.example.recipes.app.category.CategoryActivity;
import com.example.recipes.app.event.EventActivity;
import com.example.recipes.app.recipe.RecipeActivity;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;

import java.util.List;

public class MainFragment extends BaseFragment implements View.OnClickListener, IMainFragment {
    private static int DISPLAY_ITEM_COUNT = 3;

    private TextView mTvLoadMoreRecipes, mTvLoadMoreAreas, mTvLoadMoreCategories, mTvLoadMoreEvents;
    private RecyclerView mRvRecipes, mRvCategories, mRvAreas, mRvEvent;



    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.main_frag, container, false);
        
        mapping(view);

        onGetAreas();
        onGetCategories();
        onGetEvents();
        onGetRecipes();

        return view;
    }

    private void mapping(View view) {
        mTvLoadMoreRecipes = view.findViewById(R.id.tvLoadMoreRecipes);
        mTvLoadMoreRecipes.setOnClickListener(this);

        mTvLoadMoreAreas = view.findViewById(R.id.tvLoadMoreAreas);
        mTvLoadMoreAreas.setOnClickListener(this);

        mTvLoadMoreCategories = view.findViewById(R.id.tvLoadMoreCategories);
        mTvLoadMoreCategories.setOnClickListener(this);

        mTvLoadMoreEvents = view.findViewById(R.id.tvLoadMoreEvents);
        mTvLoadMoreEvents.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mTvLoadMoreRecipes) {
            onLoadMoreRecipes();
        } else if (view == mTvLoadMoreCategories) {
            onLoadMoreCategories();
        } else if (view == mTvLoadMoreAreas) {
            onLoadMoreAreas();
        } else if (view == mTvLoadMoreEvents) {
            onLoadMoreEvents();
        }
    }

    @Override
    public void onGetAreas() {
        new GetAreasAsyncTask().execute();
    }

    @Override
    public void onGetCategories() {
        new GetCategoriesAsyncTask().execute();
    }

    @Override
    public void onGetEvents() {
        new GetEventsAsyncTask().execute();
    }

    @Override
    public void onGetRecipes() {
        new GetRecipesAsyncTask().execute();
    }

    @Override
    public void onLoadMoreAreas() {
        Intent intent = new Intent(getActivity(), AreaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadMoreCategories() {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadMoreEvents() {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadMoreRecipes() {
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDisplayRecipes(List<Recipe> recipes) {
        //TODO: onDisplayRecipes
    }

    @Override
    public void onDisplayCategories(List<Category> categories) {
        //TODO: onDisplayCategories
    }

    @Override
    public void onDisplayEvents(List<Event> events) {
        //TODO: onDisplayEvents
    }

    @Override
    public void onDisplayAreas(List<Area> areas) {
        //TODO: onDisplayAreas
    }

    public static class GetRecipesAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
        //TODO: add wake fragment
        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            //TODO: GetRecipesAsyncTask
            return null;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);

            //TODO: onPostExecute GetRecipesAsyncTask
        }
    }

    public static class GetEventsAsyncTask extends AsyncTask<Void, Void, List<Event>> {
        //TODO: add wake fragment
        @Override
        protected List<Event> doInBackground(Void... voids) {
            //TODO: GetEventAsyncTask
            return null;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            //TODO: onPostExecute GetEventsAsyncTask
            super.onPostExecute(events);
        }
    }

    public static class GetCategoriesAsyncTask extends AsyncTask<Void, Void, List<Category>> {
        //TODO: add wake fragment
        @Override
        protected List<Category> doInBackground(Void... voids) {
            //TODO: GetCategoriesAsyncTask
            return null;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            //TODO: onPostExecute GetCategoriesAsyncTask
            super.onPostExecute(categories);
        }
    }

    public static class GetAreasAsyncTask extends AsyncTask<Void, Void, List<Area>> {
        //TODO: add wake fragment
        @Override
        protected List<Area> doInBackground(Void... voids) {
            //TODO:
            return null;
        }

        @Override
        protected void onPostExecute(List<Area> areas) {
            //TODO: onPostExecute GetAreasAsyncTask
            super.onPostExecute(areas);
        }
    }
}
