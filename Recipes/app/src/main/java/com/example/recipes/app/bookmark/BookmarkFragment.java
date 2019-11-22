package com.example.recipes.app.bookmark;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.app.recipe.RecipeFragment;
import com.example.recipes.app.recipe_detail.RecipeDetailActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Recipe;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BookmarkFragment extends BaseFragment implements IBookmarkFragment {
    private RecyclerView mRv;
    private List<Recipe> all;
    private List<Recipe> mFilteredRecipes;
    private RecipesRecyclerViewAdapter mRecipesRecyclerViewAdapter;

    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.bookmark_frag, container, false);
        mapping(root);

        onGetList();

        return root;
    }

    private void mapping(View root) {
        mRv = root.findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(getActivityNonNull(), 2));
    }

    @Override
    public void onGetList() {
        new GetListAsyncTask(this).execute();
    }

    @Override
    public void onDisplayList(List<Recipe> recipes) {
        all = recipes;

        mFilteredRecipes = new ArrayList<>();

        for (Recipe recipe: all) {


            if (recipe.isLiked()) {
                mFilteredRecipes.add(recipe);
            }
        }

        mRecipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(mFilteredRecipes, this);
        mRv.setAdapter(mRecipesRecyclerViewAdapter);
    }

    @Override
    public void goToRecipeDetail(Recipe recipe) {
        Intent intent = new Intent(getActivityNonNull(), RecipeDetailActivity.class);
        intent.putExtra(AppConstant.RECIPE, recipe);
        startActivity(intent);
    }

    public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Recipe> data;
        private IBookmarkFragment mFragment;

        public RecipesRecyclerViewAdapter(List<Recipe> data, IBookmarkFragment fragment) {
            this.data = data;
            mFragment = fragment;
        }

        @NonNull
        @Override
        public RecipesRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_recipe_home, viewGroup, false);
            return new RecipesRecyclerViewAdapter.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipesRecyclerViewAdapter.RecyclerViewHolder recyclerViewHolder, int i) {
            final Recipe recipe = data.get(i);
            recyclerViewHolder.mTvName.setText(recipe.getName());
            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFragment.goToRecipeDetail(recipe);
                }
            });
            if (recipe.getImage() != null && !recipe.getImage().equals("")) {
                Picasso.get().load(recipe.getImage()).into(recyclerViewHolder.mIv);
            }
            recyclerViewHolder.mTvKCal.setText(String.format("%s KCal", String.valueOf(recipe.getKcal())));
            recyclerViewHolder.mTvMinsToCook.setText(String.format("%s min", String.valueOf(recipe.getMinsToCook())));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView mTvName;
            View mView;
            ImageView mIv;
            TextView mTvMinsToCook;
            TextView mTvKCal;

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                mTvName = itemView.findViewById(R.id.tvName);
                mIv = itemView.findViewById(R.id.iv);
                mTvMinsToCook = itemView.findViewById(R.id.tvMinsToCook);
                mTvKCal = itemView.findViewById(R.id.tvKCal);
            }
        }
    }


    private class GetListAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
        private WeakReference<IBookmarkFragment> mWeakReferencesFragment;

        public GetListAsyncTask(IBookmarkFragment fragment) {
            mWeakReferencesFragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            List<Recipe> all = db.getRecipeDao().getAll();

            return all;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            mWeakReferencesFragment.get().onDisplayList(recipes);
        }
    }
}
