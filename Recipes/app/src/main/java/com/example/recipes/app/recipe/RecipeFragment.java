package com.example.recipes.app.recipe;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.app.recipe_detail.RecipeDetailActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.example.recipes.model.Area;
import com.example.recipes.model.Recipe;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends BaseFragment implements IRecipeFragment, View.OnClickListener {
    private RecyclerView mRv;

    private Area mArea;
    private Event mEvent;
    private Category mCategory;
    RecipesRecyclerViewAdapter mRecipesRecyclerViewAdapter;
    private List<Recipe> all;
    private List<Recipe> mFilteredRecipes;
    private FilterDialogFragment mfilterDialogFragment;

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
        
        getDataFromIntent();
        
        onGetList();
        return inflate;
    }

    private void onFilter(String keyword, Area area, Category category, Event event) {
        List<Recipe> recipes  = new ArrayList<>();
        for (Recipe recipe: all) {
            boolean valid = true;

            if (! recipe.getName().contains(keyword)) {
                valid = false;
            }
            if (area != null) {
                if (area.getUid() != recipe.getAreaId()) {
                    valid = false;
                }
            }

            if (category != null) {
                if (category.getUid() != recipe.getCategoryId()) {
                    valid = false;
                }
            }

            if (event != null) {
                if (event.getUid() != recipe.getEventId()) {
                    valid = false;
                }
            }

            if (valid) {
                recipes.add(recipe);
            }
        }

        mFilteredRecipes.clear();
        mFilteredRecipes.addAll(recipes);
        mRecipesRecyclerViewAdapter.notifyDataSetChanged();

        mfilterDialogFragment.dismiss();
    }

    private void getDataFromIntent() {
        Intent intent = getActivityNonNull().getIntent();

        mEvent = (Event) intent.getSerializableExtra(AppConstant.EVENT);
        mArea = (Area) intent.getSerializableExtra(AppConstant.AREA);
        mCategory = (Category) intent.getSerializableExtra(AppConstant.CATEGORY);

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
        all = recipes;

        mFilteredRecipes = new ArrayList<>();

        for (Recipe recipe: all) {
            boolean valid = true;

            if (mArea != null) {
                if (mArea.getUid() != recipe.getAreaId()) {
                    valid = false;
                }
            }

            if (mCategory != null) {
                if (mCategory.getUid() != recipe.getCategoryId()) {
                    valid = false;
                }
            }

            if (mEvent != null) {
                if (mEvent.getUid() != recipe.getEventId()) {
                    valid = false;
                }
            }

            if (valid) {
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

    @Override
    public void openPopupFilter() {
        mfilterDialogFragment =  new FilterDialogFragment(getActivityNonNull(), this, mArea, mCategory, mEvent);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        mfilterDialogFragment.show(ft, "dialog");

    }

    public static class FilterDialogFragment extends DialogFragment {
        private List<Area> mAreas;
        private List<Category> mCategories;
        private List<Event> mEvents;

        private ArrayAdapter<String> mSpinnerAreaAdapter;
        private ArrayAdapter<String> mSpinnerCategoryAdapter;
        private ArrayAdapter<String> mSpinnerEventAdapter;

        private Spinner mSpArea;
        private Spinner mSpCategory;
        private Spinner mSpEvent;

        private Area mArea;
        private Category mCategory;
        private Event mEvent;

        private EditText metKeyword;

        private Button mbtnAdd;

        private Activity mActivity;
        private RecipeFragment mRecipeFragment;

        public FilterDialogFragment(Activity activity, RecipeFragment recipeFragment, Area area, Category category, Event event) {
            mActivity = activity;
            mArea = area;
            mCategory = category;
            mEvent = event;
            mRecipeFragment = recipeFragment;
        }


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.recipe_dialog_frag, container, false);

            mapping(root);

            getListAreas();

            getListCategories();

            getListEvents();

            return root;
        }

        private void mapping(View view) {
            mSpArea = view.findViewById(R.id.spArea);
            mSpCategory = view.findViewById(R.id.spCategory);
            mSpEvent = view.findViewById(R.id.spEvent);

            mbtnAdd = view.findViewById(R.id.btnAdd);
            mbtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearch();
                }
            });

            metKeyword = view.findViewById(R.id.etKeyword);
        }

        private void onSearch() {
            String keyword = metKeyword.getText().toString();
            Area area = null;
            Category category = null;
            Event event = null;

            int areaPosition = mSpArea.getSelectedItemPosition();
            if (areaPosition > 0) {
                area = mAreas.get(areaPosition - 1);
            }

            int categoryPosition = mSpCategory.getSelectedItemPosition();
            if (categoryPosition > 0) {
                category = mCategories.get(categoryPosition - 1);
            }

            int eventPosition = mSpEvent.getSelectedItemPosition();
            if (eventPosition > 0) {
                event = mEvents.get(eventPosition - 1);
            }

            mRecipeFragment.onFilter(keyword, area, category, event);

        }

        public void getListAreas() {
            new FilterDialogFragment.GetListAreasAsyncTask(this).execute();
        }

        public void getListCategories() {
            new FilterDialogFragment.GetListCategoriesAsyncTask(this).execute();
        }

        public void getListEvents() {
            new FilterDialogFragment.GetListEventsAsyncTask(this).execute();
        }

        public void displayListAreas(List<Area> areas) {
            mAreas = areas;
            List<String> areaAsStrings = new ArrayList<>();
            areaAsStrings.add("Chọn tất cả");
            for (Area area: areas ) {
                areaAsStrings.add(area.getName());
            }
            mSpinnerAreaAdapter = new ArrayAdapter<String>(mActivity,
                    android.R.layout.simple_spinner_item, areaAsStrings);
            mSpinnerAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpArea.setAdapter(mSpinnerAreaAdapter);

            if (mArea != null) {
                for (int i = 0; i < mAreas.size(); i++) {
                    Area area = mAreas.get(i);
                    if (area.getUid() == mArea.getUid()) {
                        mSpArea.setSelection(i);
                        break;
                    }
                }
            }
        }

        public void displayListCategories(List<Category> categories) {
            mCategories = categories;
            List<String> categoryAsStrings = new ArrayList<>();
            categoryAsStrings.add("Chọn tất cả");
            for (Category category: categories) {
                categoryAsStrings.add(category.getName());
            }
            mSpinnerCategoryAdapter = new ArrayAdapter<String>(mActivity,
                    android.R.layout.simple_spinner_item, categoryAsStrings);
            mSpinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpCategory.setAdapter(mSpinnerCategoryAdapter);

            if (mCategory != null) {
                for (int i = 0; i < mCategories.size(); i++) {
                    Category category = mCategories.get(i);
                    if (category.getUid() == mCategory.getUid()) {
                        mSpCategory.setSelection(i);
                        break;
                    }
                }
            }
        }

        public void displayListEvents(List<Event> events) {
            mEvents = events;
            List<String> eventAsStrings = new ArrayList<>();
            eventAsStrings.add("Chọn tất cả");
            for (Event event: events) {
                eventAsStrings.add(event.getName());
            }
            mSpinnerEventAdapter = new ArrayAdapter<String>(mActivity,
                    android.R.layout.simple_spinner_item, eventAsStrings);
            mSpinnerEventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpEvent.setAdapter(mSpinnerEventAdapter);

            if (mEvent != null) {
                for (int i = 0; i < mEvents.size(); i++) {
                    Event event = mEvents.get(i);
                    if (event.getUid() == mEvent.getUid()) {
                        mSpEvent.setSelection(i);
                        break;
                    }
                }
            }
        }

        private class GetListAreasAsyncTask extends AsyncTask<Void, Void, List<Area>> {
            private WeakReference<FilterDialogFragment> mWeakReferenceFragment;
            public GetListAreasAsyncTask(FilterDialogFragment fragment) {
                mWeakReferenceFragment = new WeakReference<>(fragment);
            }

            @Override
            protected List<Area> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(mActivity);
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
            private WeakReference<FilterDialogFragment> mWeakReferenceFragment;
            public GetListCategoriesAsyncTask(FilterDialogFragment fragment) {
                mWeakReferenceFragment = new WeakReference<>(fragment);
            }

            @Override
            protected List<Category> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(mActivity);
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
            private WeakReference<FilterDialogFragment> mWeakReferenceFragment;
            public GetListEventsAsyncTask(FilterDialogFragment fragment) {
                mWeakReferenceFragment = new WeakReference<>(fragment);
            }

            @Override
            protected List<Event> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(mActivity);
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
