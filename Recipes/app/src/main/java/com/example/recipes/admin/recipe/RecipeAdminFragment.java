package com.example.recipes.admin.recipe;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.admin.area.AreaAdminFragment;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecipeAdminFragment extends BaseFragment implements IRecipeAdminFragment, View.OnClickListener {
    private FloatingActionButton mBtnAdd;
    private RecyclerView mRv;

    public RecipeAdminFragment() {
        // Required empty public constructor
    }

    public static RecipeAdminFragment newInstance() {
        return new RecipeAdminFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.recipe_admin_frag, container, false);
        mapping(inflate);
        getList();
        return inflate;
    }

    private void mapping(View view) {
        mBtnAdd = view.findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(layoutManager);
    }

    @Override
    public void getList() {
        new GetListAsyncTask().execute();
    }

    @Override
    public void goToAddRecipeFragment() {
        startActivity(new Intent(getActivityNonNull(), RecipeAdminDetailActivity.class));
    }

    @Override
    public void displayList(List<Recipe> recipes) {
        mRv.setAdapter(new RecipesRecyclerViewAdapter(recipes));
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            goToAddRecipeFragment();
        }
    }
    public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Recipe> data;

        public RecipesRecyclerViewAdapter(List<Recipe> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_recipe, viewGroup, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            Recipe recipe = data.get(i);
            recyclerViewHolder.mTvName.setText(recipe.getName());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView mTvName;
            public RecyclerViewHolder(View itemView) {
                super(itemView);

                mTvName = itemView.findViewById(R.id.tvName);
            }
        }
    }


    private class GetListAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            List<Recipe> all = db.getRecipeDao().getAll();
            displayList(all);

            return null;
        }
    }
}
