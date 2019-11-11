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
import com.example.recipes.app.category.ICategoryFragment;
import com.example.recipes.app.event.EventActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;


public class RecipeDetailFragment extends BaseFragment implements IRecipeDetailFragment, View.OnClickListener {

    private Recipe mRecipe;

    private TextView mTvCategoryName, mTvAreaName, mTvEventName, mTvRecipeName, mTvIngredients, mTvDetail;
    private ImageView mIvRecipe;
    private TextView mTvKCal;
    private TextView mTvMinsToCook;

    private FloatingActionButton mBtnShare;

    public static RecipeDetailFragment getInstance() {
        return new RecipeDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recipe_detail_frag, container, false);

        getDataFromIntent();

        mapping(view);

        onDisplayData(mRecipe);

        return view;
    }

    @Override
    public void getDataFromIntent() {
        mRecipe = (Recipe) getActivityNonNull().getIntent().getSerializableExtra(AppConstant.RECIPE);
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

        mBtnShare = view.findViewById(R.id.btnShare);
        mBtnShare.setOnClickListener(this);


        mTvMinsToCook = view.findViewById(R.id.tvMinsToCook);
        mTvKCal = view.findViewById(R.id.tvKCal);
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
        } else if (view == mBtnShare) {
            onShareOnFacebook();
        }
    }

    @Override
    public void onDisplayData(Recipe recipe) {
        mTvDetail.setText(recipe.getDetail());
        mTvRecipeName.setText(recipe.getName());


        mTvIngredients.setText(recipe.getIngredients());

        mTvKCal.setText(String.format("%skCal", String.valueOf(recipe.getKcal())));
        mTvMinsToCook.setText(String.format("%s phút", String.valueOf(recipe.getMinsToCook())));

        if (recipe.getImage() != null && !recipe.getImage().equals("")) {
            Picasso.get().load(recipe.getImage()).into(mIvRecipe);
        }

        new ObtainRecipeInfoAsyncTask(this, recipe).execute();
    }

    @Override
    public void onShareOnFacebook() {
        //TODO: share on facebook
    }

    @Override
    public void onDisplayMoreInfo(Recipe recipe) {
        mTvCategoryName.setText(recipe.getCategory().getName());
        mTvEventName.setText(recipe.getEvent().getName());
        mTvAreaName.setText(recipe.getArea().getName());
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

    private class ObtainRecipeInfoAsyncTask extends AsyncTask<Void, Void, Recipe> {
        private Recipe mRecipe;

        private WeakReference<IRecipeDetailFragment> mWeakReferenceFragment;

        public ObtainRecipeInfoAsyncTask(IRecipeDetailFragment fragment, Recipe recipe) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
            mRecipe = recipe;
        }
        @Override
        protected Recipe doInBackground(Void... voids) {

            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            Category category = db.getCategoryDao().findById(mRecipe.getCategoryId());
            Area area = db.getAreaDao().findById(mRecipe.getAreaId());
            Event event = db.getEventDao().findById(mRecipe.getEventId());

            mRecipe.setCategory(category);
            mRecipe.setEvent(event);
            mRecipe.setArea(area);

            return mRecipe;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            super.onPostExecute(recipe);
            mWeakReferenceFragment.get().onDisplayMoreInfo(recipe);
        }
    }
}
