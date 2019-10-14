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
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    public void getList() {
        new GetListAsyncTask().execute();
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
    public void displayList(List<Category> categories) {
        mRvCategories.setAdapter(new CategoriesRecyclerViewAdapter(categories));
    }


    public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Category> data;

        public CategoriesRecyclerViewAdapter(List<Category> data) {
            this.data = data;
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
            Category category = data.get(i);
            recyclerViewHolder.mTvName.setText(category.getName());
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

            List<Category> all = db.getCategoryDao().getAll();
            displayList(all);

            return null;
        }
    }
}
