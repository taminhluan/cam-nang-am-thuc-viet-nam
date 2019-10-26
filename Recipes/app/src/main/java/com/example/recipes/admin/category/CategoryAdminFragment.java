package com.example.recipes.admin.category;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

public class CategoryAdminFragment extends BaseFragment implements View.OnClickListener, ICategoryAdminFragment {

    private FloatingActionButton mBtnAdd;
    private RecyclerView mRvCategories;

    public CategoryAdminFragment() {
        // Required empty public constructor
    }

    public static CategoryAdminFragment newInstance() {
        return new CategoryAdminFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.category_admin_frag, container, false);
        mapping(inflate);

        getList();

        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        getList();
    }

    @Override
    public void getList() {
        new GetListAsyncTask(this).execute();
    }

    private void mapping(View view) {
        mBtnAdd = view.findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRvCategories = view.findViewById(R.id.rvCategories);
        mRvCategories.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            goToAddCategoryFragment();
        }
    }

    @Override
    public void goToAddCategoryFragment() {
        startActivity(new Intent(getActivityNonNull(), CategoryAdminDetailActivity.class));
    }

    @Override
    public void goToUpdateCategoryFragment(Category category) {
        Intent intent = new Intent(getActivityNonNull(), CategoryAdminDetailActivity.class);
        intent.putExtra(AppConstant.CATEGORY, category);
        startActivity(intent);

    }

    @Override
    public void displayList(List<Category> categories) {
        mRvCategories.setAdapter(new CategoriesRecyclerViewAdapter(categories, this));
    }


    public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Category> data;
        private ICategoryAdminFragment mFragment;

        public CategoriesRecyclerViewAdapter(List<Category> data, ICategoryAdminFragment fragment) {
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
                    mFragment.goToUpdateCategoryFragment(category);
                }
            });

            if (category.getImage() != null && !category.getImage().equals("")) {
                Picasso.get().load(category.getImage()).into(recyclerViewHolder.mIv);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView mTvName;
            View mView;
            ImageView mIv;
            public RecyclerViewHolder(View itemView) {
                super(itemView);

                mTvName = itemView.findViewById(R.id.tvName);
                mView = itemView;
                mIv = itemView.findViewById(R.id.iv);
            }
        }
    }

    private class GetListAsyncTask extends AsyncTask<Void, Void, List<Category>> {
        private WeakReference<ICategoryAdminFragment> mWeakReferenceFragment;

        public GetListAsyncTask(ICategoryAdminFragment fragment) {
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
            mWeakReferenceFragment.get().displayList(categories);
        }
    }
}
