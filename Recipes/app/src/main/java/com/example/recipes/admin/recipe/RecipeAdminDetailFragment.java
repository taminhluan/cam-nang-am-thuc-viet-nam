package com.example.recipes.admin.recipe;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.admin.area.AreaAdminDetailFragment;
import com.example.recipes.app.recipe_detail.IRecipeDetailFragment;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.example.recipes.model.Recipe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    private Spinner mSpArea;
    private Spinner mSpCategory;
    private Spinner mSpEvent;

    private ArrayAdapter<String> mSpinnerAreaAdapter;
    private ArrayAdapter<String> mSpinnerCategoryAdapter;
    private ArrayAdapter<String> mSpinnerEventAdapter;

    private ImageView mIvImage;

    private List<Area> mAreas;
    private List<Category> mCategories;
    private List<Event> mEvents;

    public RecipeAdminDetailFragment() {
        mRecipe = new Recipe();
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
        mSpArea = view.findViewById(R.id.spArea);
        mSpCategory = view.findViewById(R.id.spCategory);
        mSpEvent = view.findViewById(R.id.spEvent);
    }

    private void initLayout() {
        getListAreas();
        getListCategories();
        getListEvents();
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
        mEtId.setText(String.valueOf(recipe.getUid()));
        mEtImage.setText(recipe.getImage());
        mEtName.setText(recipe.getName());

        //TODO display category, event, area

        //TODO display image for ImageView
    }

    @Override
    public void add(Recipe recipe) {
        obtainValueFromScreen();
        new AddAsyncTask(this).execute();
    }

    @Override
    public void update(Recipe recipe) {
        obtainValueFromScreen();
        new UpdateAsyncTask(this).execute();
    }

    @Override
    public void delete(Recipe recipe) {
        obtainValueFromScreen();
        new DeleteAsyncTask(this).execute();
    }

    @Override
    public void getListAreas() {
        new GetListAreasAsyncTask(this).execute();
    }

    @Override
    public void getListCategories() {
        new GetListCategoriesAsyncTask(this).execute();
    }

    @Override
    public void getListEvents() {
        new GetListEventsAsyncTask(this).execute();
    }

    @Override
    public void displayListAreas(List<Area> areas) {
        mAreas = areas;
        List<String> areaAsStrings = new ArrayList<>();
        for (Area area: areas ) {
            areaAsStrings.add(area.getName());
        }
        mSpinnerAreaAdapter = new ArrayAdapter<String>(getActivityNonNull(),
                android.R.layout.simple_spinner_item, areaAsStrings);
        mSpinnerAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpArea.setAdapter(mSpinnerAreaAdapter);

        for (int i = 0; i < mAreas.size(); i++) {
            Area area = mAreas.get(i);
            if (area.getUid() == mRecipe.getAreaId()) {
                mSpArea.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void displayListCategories(List<Category> categories) {
        mCategories = categories;
        List<String> categoryAsStrings = new ArrayList<>();
        for (Category category: categories) {
            categoryAsStrings.add(category.getName());
        }
        mSpinnerCategoryAdapter = new ArrayAdapter<String>(getActivityNonNull(),
                android.R.layout.simple_spinner_item, categoryAsStrings);
        mSpinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpCategory.setAdapter(mSpinnerCategoryAdapter);

        for (int i = 0; i < mCategories.size(); i++) {
            Category category = mCategories.get(i);
            if (category.getUid() == mRecipe.getCategoryId()) {
                mSpCategory.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void displayListEvents(List<Event> events) {
        mEvents = events;
        List<String> eventAsStrings = new ArrayList<>();
        for (Event event: events) {
            eventAsStrings.add(event.getName());
        }
        mSpinnerEventAdapter = new ArrayAdapter<String>(getActivityNonNull(),
                android.R.layout.simple_spinner_item, eventAsStrings);
        mSpinnerEventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpEvent.setAdapter(mSpinnerEventAdapter);

        for (int i = 0; i < mEvents.size(); i++) {
            Event event = mEvents.get(i);
            if (event.getUid() == mRecipe.getEventId()) {
                mSpEvent.setSelection(i);
                break;
            }
        }
    }

    // get value from edittext
    // save to mArea
    private void obtainValueFromScreen() {
        mRecipe.setImage(mEtImage.getText().toString());
        mRecipe.setName(mEtName.getText().toString());

        int areaId = mAreas.get(mSpArea.getSelectedItemPosition()).getUid();
        mRecipe.setAreaId(areaId);

        int categoryId = mCategories.get(mSpCategory.getSelectedItemPosition()).getUid();
        mRecipe.setCategoryId(categoryId);

        int eventId = mEvents.get(mSpEvent.getSelectedItemPosition()).getUid();
        mRecipe.setEventId(eventId);
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
        private WeakReference<IRecipeAdminDetailFragment> mWeakReferenceFragment;

        public AddAsyncTask(IRecipeAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getRecipeDao().insertAll(mRecipe);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<IRecipeAdminDetailFragment> mWeakReferenceFragment;
        public UpdateAsyncTask(IRecipeAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getRecipeDao().updateAll(mRecipe);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<IRecipeAdminDetailFragment> mWeakReferenceFragment;
        public DeleteAsyncTask(IRecipeAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getRecipeDao().delete(mRecipe);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }

    private class GetListAreasAsyncTask extends AsyncTask<Void, Void, List<Area>> {
        private WeakReference<IRecipeAdminDetailFragment> mWeakReferenceFragment;
        public GetListAreasAsyncTask(IRecipeAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<Area> doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());
            List<Area> all = db.getAreaDao().getAll();
            return all;
        }

        @Override
        protected void onPostExecute(List<Area> areas) {
            super.onPostExecute(areas);
            mWeakReferenceFragment.get().displayListAreas(areas);
        }
    }
    private class GetListCategoriesAsyncTask extends AsyncTask<Void, Void, List<Category>> {
        private WeakReference<IRecipeAdminDetailFragment> mWeakReferenceFragment;
        public GetListCategoriesAsyncTask(IRecipeAdminDetailFragment fragment) {
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
            mWeakReferenceFragment.get().displayListCategories(categories);
        }
    }

    private class GetListEventsAsyncTask extends AsyncTask<Void, Void, List<Event>> {
        private WeakReference<IRecipeAdminDetailFragment> mWeakReferenceFragment;
        public GetListEventsAsyncTask(IRecipeAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<Event> doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());
            List<Event> all = db.getEventDao().getAll();
            return all;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            super.onPostExecute(events);
            mWeakReferenceFragment.get().displayListEvents(events);
        }
    }
}
