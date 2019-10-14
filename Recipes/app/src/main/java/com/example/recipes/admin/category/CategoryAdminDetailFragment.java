package com.example.recipes.admin.category;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Category;

public class CategoryAdminDetailFragment extends BaseFragment implements ICategoryAdminDetailFragment, View.OnClickListener {
    private Category mCategory;

    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    private EditText mEtId;
    private EditText mEtImage;
    private EditText mEtName;

    private LinearLayout mLlUpdateGroupButtons;
    private LinearLayout mLlAddGroupButtons;

    private ImageView mIvImage;

    public CategoryAdminDetailFragment() {
        mCategory = new Category();
    }

    public CategoryAdminDetailFragment(Category category) {
        mCategory = category;
    }

    public static CategoryAdminDetailFragment newInstance() {
        return new CategoryAdminDetailFragment();
    }

    public static CategoryAdminDetailFragment newInstance(Category category) {
        return new CategoryAdminDetailFragment(category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.category_admin_detail_frag, container, false);
        mapping(inflate);
        initLayout();
        return inflate;
    }

    private void mapping(View view) {
        mEtId = view.findViewById(R.id.etId);
        mEtImage = view.findViewById(R.id.etImage);
        mEtName = view.findViewById(R.id.etName);

        mBtnAdd = view.findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(this);
        mBtnUpdate = view.findViewById(R.id.btnUpdate);
        mBtnUpdate.setOnClickListener(this);
        mBtnDelete = view.findViewById(R.id.btnDelete);
        mBtnDelete.setOnClickListener(this);

        mLlUpdateGroupButtons = view.findViewById(R.id.llUpdateGroupButtons);
        mLlAddGroupButtons = view.findViewById(R.id.llCreateGroupButtons);

        mIvImage = view.findViewById(R.id.ivImage);
    }

    private void initLayout() {
        hideGroupButton();
        display(mCategory);
    }

    private void hideGroupButton() {
        if (mCategory.getUid() == 0) { //CREATE => hide update button
            mLlUpdateGroupButtons.setVisibility(View.GONE);
        } else {
            mLlAddGroupButtons.setVisibility(View.GONE);
        }
    }

    @Override
    public void display(Category category) {
        mEtId.setText(category.getUid());
        mEtImage.setText(category.getImage());
        mEtName.setText(category.getName());
    }

    @Override
    public void add(Category category) {
        new AddAsyncTask().execute();
    }

    @Override
    public void update(Category category) {
        new UpdateAsyncTask().execute();
    }

    @Override
    public void delete(Category category) {
        new DeleteAsyncTask().execute();
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }
    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            add(mCategory);
        } else if (view == mBtnDelete) {
            delete(mCategory);
        } else if (view == mBtnUpdate) {
            update(mCategory);
        }
    }

    private class AddAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getCategoryDao().insertAll(mCategory);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            back();
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getCategoryDao().updateAll(mCategory);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            back();
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getCategoryDao().delete(mCategory);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            back();
        }
    }
}
