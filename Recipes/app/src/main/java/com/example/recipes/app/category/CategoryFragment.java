package com.example.recipes.app.category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.recipes.admin.category.CategoryAdminFragment;
import com.example.recipes.admin.category.ICategoryAdminFragment;
import com.example.recipes.app.area.AreaFragment;
import com.example.recipes.app.recipe.RecipeActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Recipe;

import java.lang.ref.WeakReference;
import java.util.List;

public class CategoryFragment extends BaseFragment implements ICategoryFragment, View.OnClickListener {
    private RecyclerView mRv;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.category_frag, container, false);
        mapping(inflate);
        onGetList();
        return inflate;
    }

    private void mapping(View view) {
        mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(getActivityNonNull(), 2));
    }

    @Override
    public void onGetList() {
        new GetListAsyncTask(this).execute();
    }

    @Override
    public void onDisplayList(List<Category> categories) {
        mRv.setAdapter(new CategoriesRecyclerViewAdapter(categories, this));
    }

    @Override
    public void goToRecipesByCategory(Category category) {
        Intent intent = new Intent(getActivityNonNull(), RecipeActivity.class);
        intent.putExtra(AppConstant.CATEGORY, category);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Category> data;
        private ICategoryFragment mFragment;

        public CategoriesRecyclerViewAdapter(List<Category> data, ICategoryFragment fragment) {
            this.data = data;
            mFragment = fragment;
        }

        @NonNull
        @Override
        public CategoriesRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_category, viewGroup, false);
            return new CategoriesRecyclerViewAdapter.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriesRecyclerViewAdapter.RecyclerViewHolder recyclerViewHolder, int i) {
            final Category category = data.get(i);
            recyclerViewHolder.mTvName.setText(category.getName());
            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFragment.goToRecipesByCategory(category);
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

                mTvName = itemView.findViewById(R.id.tvName);
                mView = itemView;
            }
        }
    }

    private class GetListAsyncTask extends AsyncTask<Void, Void, List<Category>> {
        private WeakReference<ICategoryFragment> mWeakReferenceFragment;

        public GetListAsyncTask(ICategoryFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected List<Category> doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            List<Category> all = db.getCategoryDao().getAll();

            return all;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
            mWeakReferenceFragment.get().onDisplayList(categories);
        }
    }
}
