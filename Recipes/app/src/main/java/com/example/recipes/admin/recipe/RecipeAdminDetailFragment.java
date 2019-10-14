package com.example.recipes.admin.recipe;


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
import com.example.recipes.admin.area.AreaAdminDetailFragment;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Recipe;

public class RecipeAdminDetailFragment extends BaseFragment implements IRecipeAdminDetailFragment, View.OnClickListener{
    private Recipe mRecipe;

    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    private EditText mEtId;
    private EditText mEtImage;
    private EditText mEtName;

    private LinearLayout mLlUpdateGroupButtons;
    private LinearLayout mLlAddGroupButtons;

    private ImageView mIvImage;

    public RecipeAdminDetailFragment() {
        // Required empty public constructor
    }

    public RecipeAdminDetailFragment(Recipe recipe) {
        mRecipe = recipe;
    }

    public static RecipeAdminDetailFragment newInstance() {
        return new RecipeAdminDetailFragment();
    }

    public static RecipeAdminDetailFragment newInstance(Recipe recipe) {
        return new RecipeAdminDetailFragment(recipe);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.recipe_admin_detail_frag, container, false);
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
        display(mRecipe);
    }

    private void hideGroupButton() {
        if (mRecipe.getUid() == 0) { //CREATE => hide update button
            mLlUpdateGroupButtons.setVisibility(View.GONE);
        } else {
            mLlAddGroupButtons.setVisibility(View.GONE);
        }
    }

    @Override
    public void display(Recipe recipe) {
        mEtId.setText(recipe.getUid());
        mEtImage.setText(recipe.getImage());
        mEtName.setText(recipe.getName());

        //TODO display image for ImageView
    }

    @Override
    public void add(Recipe recipe) {
        obtainValueFromScreen();
        new AddAsyncTask().execute();
    }

    @Override
    public void update(Recipe recipe) {
        obtainValueFromScreen();
        new UpdateAsyncTask().execute();
    }

    @Override
    public void delete(Recipe recipe) {
        obtainValueFromScreen();
        new DeleteAsyncTask().execute();
    }

    // get value from edittext
    // save to mArea
    private void obtainValueFromScreen() {
        mRecipe.setImage(mEtImage.getText().toString());
        mRecipe.setName(mEtName.getText().toString());
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            add(mRecipe);
        } else if (view == mBtnDelete) {
            delete(mRecipe);
        } else if (view == mBtnUpdate) {
            update(mRecipe);
        }
    }

    private class AddAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getRecipeDao().insertAll(mRecipe);
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

            db.getRecipeDao().updateAll(mRecipe);

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

            db.getRecipeDao().delete(mRecipe);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            back();
        }
    }
}
