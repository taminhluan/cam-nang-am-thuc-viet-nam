package com.example.recipes.app.recipe;


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
import com.example.recipes.admin.recipe.IRecipeAdminFragment;
import com.example.recipes.admin.recipe.RecipeAdminFragment;
import com.example.recipes.app.recipe_detail.RecipeDetailActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Recipe;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecipeFragment extends BaseFragment implements IRecipeFragment, View.OnClickListener {
    private RecyclerView mRv;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.recipe_frag, container, false);
        mapping(inflate);
        onGetList();
        return inflate;
    }

    private void mapping(View view) {
        mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(getActivityNonNull(), 2));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetList() {
        new GetListAsyncTask(this).execute();
    }

    @Override
    public void onDisplayList(List<Recipe> recipes) {
        mRv.setAdapter(new RecipesRecyclerViewAdapter(recipes, this));
    }

    @Override
    public void goToRecipeDetail(Recipe recipe) {
        Intent intent = new Intent(getActivityNonNull(), RecipeDetailActivity.class);
        intent.putExtra(AppConstant.RECIPE, recipe);
        startActivity(intent);
    }

    public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Recipe> data;
        private IRecipeFragment mFragment;

        public RecipesRecyclerViewAdapter(List<Recipe> data, IRecipeFragment fragment) {
            this.data = data;
            mFragment = fragment;
        }

        @NonNull
        @Override
        public RecipesRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_recipe, viewGroup, false);
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
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView mTvName;
            View mView;
            public RecyclerViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                mTvName = itemView.findViewById(R.id.tvName);
            }
        }
    }


    private class GetListAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
        private WeakReference<IRecipeFragment> mWeakReferencesFragment;

        public GetListAsyncTask(IRecipeFragment fragment) {
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
