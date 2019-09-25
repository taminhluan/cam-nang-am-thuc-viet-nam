package com.example.recipes.app.recipe_detail;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.app.area.AreaActivity;
import com.example.recipes.app.category.CategoryActivity;
import com.example.recipes.app.event.EventActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;

import java.lang.ref.WeakReference;


public class RecipeDetailFragment extends BaseFragment implements IRecipeDetailFragment, View.OnClickListener {

    private final int mRecipeId;
    private Recipe mRecipe;

    private TextView mTvCategoryName, mTvAreaName, mTvEventName, mTvRecipeName, mTvIngredients, mTvDetail;
    private ImageView mIvRecipe;

    public RecipeDetailFragment(int recipeId) {
        mRecipeId = recipeId;
    }

    public static RecipeDetailFragment getInstance(int recipeId) {
        return new RecipeDetailFragment(recipeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recipe_detail_frag, container, false);

        onGetData(mRecipeId);

        mapping(view);

        return view;
    }

    private void mapping(View view) {
        mTvCategoryName = view.findViewById(R.id.tvCategoryName);
        mTvCategoryName.setOnClickListener(this);

        mTvAreaName = view.findViewById(R.id.tvAreaName);
        mTvAreaName.setOnClickListener(this);

        mTvEventName = view.findViewById(R.id.tvEventName);
        mTvEventName.setOnClickListener(this);

        mTvRecipeName = view.findViewById(R.id.tvRecipeName);
        mTvIngredients = view.findViewById(R.id.tvIngredients);
        mTvDetail = view.findViewById(R.id.tvDetail);

        mIvRecipe = view.findViewById(R.id.ivRecipe);
    }

    @Override
    public void onClick(View view) {
        if (mRecipe == null) {
            return;
        }

        if (view == mTvCategoryName) {
            onGoToCategory();
        } else if (view == mTvAreaName) {
            onGoToArea();
        } else if (view == mTvEventName) {
            onGoToEvent();
        }
    }

    @Override
    public void onGetData(int id) {
        //TODO: on get recipe data with async task
    }

    @Override
    public void onDisplayData(Recipe recipe) {
        mTvCategoryName.setText(recipe.getCategory().getName());
        mTvEventName.setText(recipe.getEvent().getName());
        mTvAreaName.setText(recipe.getArea().getName());
        mTvDetail.setText(recipe.getDetail());
        mTvIngredients.setText(recipe.getIngredients());

        //TODO: display images
    }

    @Override
    public void onShareOnFacebook() {
        //TODO: share on facebook
    }

    @Override
    public void onGoToArea() {
        Area area = mRecipe.getArea();

        Intent intent = new Intent(this.getActivity(), AreaActivity.class);
        intent.putExtra(AppConstant.AREA_ID_KEY, area.getUid());
        startActivity(intent);

        getActivityNonNull().finish();
    }

    @Override
    public void onGoToEvent() {
        Event event = mRecipe.getEvent();

        Intent intent = new Intent(this.getActivity(), EventActivity.class);
        intent.putExtra(AppConstant.EVENT_ID_KEY, event.getUid());

        startActivity(intent);

        getActivityNonNull().finish();
    }

    @Override
    public void onGoToCategory() {
        Category category = mRecipe.getCategory();

        Intent intent = new Intent(this.getActivity(), CategoryActivity.class);
        intent.putExtra(AppConstant.CATEGORY_ID_KEY, category.getUid());

        startActivity(intent);

        getActivityNonNull().finish();
    }

    private static class GetRecipeDataAsyncTask extends AsyncTask<Void, Void, Recipe> {
        private WeakReference<RecipeDetailFragment> mRecipeDetailFragment;
        private AppDatabase mdb;

        public GetRecipeDataAsyncTask(RecipeDetailFragment recipeDetailFragment) {
            mRecipeDetailFragment = new WeakReference<>(recipeDetailFragment);
            mdb = AppDatabase.getInstance(mRecipeDetailFragment.get().getContext());
        }

        @Override
        protected Recipe doInBackground(Void... voids) {
            //TODO: get recipe data do in background
            return null;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            super.onPostExecute(recipe);

            obtainData(recipe);

            IRecipeDetailFragment recipeDetailFragment = mRecipeDetailFragment.get();
            recipeDetailFragment.onDisplayData(recipe);
        }

        private void obtainData(Recipe recipe) {
            //TODO: obtain recipe

        }
    }
}
