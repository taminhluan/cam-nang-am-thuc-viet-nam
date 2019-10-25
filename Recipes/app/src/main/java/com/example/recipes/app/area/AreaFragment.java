package com.example.recipes.app.area;


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
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.admin.area.AreaAdminFragment;
import com.example.recipes.admin.area.IAreaAdminFragment;
import com.example.recipes.app.recipe.RecipeActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Recipe;

import java.lang.ref.WeakReference;
import java.util.List;

public class AreaFragment extends BaseFragment implements IAreaFragment {

    private RecyclerView mRvAreas;


    public AreaFragment() {
        // Required empty public constructor
    }

    public static AreaFragment newInstance() {
        return new AreaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.area_frag, container, false);
        mapping(inflate);
        onGetList();
        return inflate;
    }

    private void mapping(View view) {
        mRvAreas = view.findViewById(R.id.rvAreas);
        mRvAreas.setLayoutManager(new GridLayoutManager(getActivityNonNull(), 2));
    }

    @Override
    public void onGetList() {
        new GetListAsyncTask(this).execute();
    }

    @Override
    public void onDisplayList(List<Area> areas) {
        mRvAreas.setAdapter(new AreasRecyclerViewAdapter(areas, this));
    }

    @Override
    public void goToRecipesByArea(Area area) {
        Intent intent = new Intent(getActivityNonNull(), RecipeActivity.class);
        intent.putExtra(AppConstant.AREA, area);
        startActivity(intent);
    }

    public class AreasRecyclerViewAdapter extends RecyclerView.Adapter<AreasRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Area> data;
        private IAreaFragment mFragment;

        public AreasRecyclerViewAdapter(List<Area> data, IAreaFragment fragment) {
            this.data = data;
            mFragment = fragment;
        }

        @NonNull
        @Override
        public AreasRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            //TODO layout for this
            View view = inflater.inflate(R.layout.item_area, viewGroup, false);
            return new AreasRecyclerViewAdapter.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AreasRecyclerViewAdapter.RecyclerViewHolder recyclerViewHolder, int i) {
            final Area area = data.get(i);
            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFragment.goToRecipesByArea(area);
                }
            });
            recyclerViewHolder.mTvName.setText(area.getName());
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

    private class GetListAsyncTask extends AsyncTask<Void, Void, List<Area>> {
        private WeakReference<IAreaFragment> mWeakReferencesFragment;

        public GetListAsyncTask(IAreaFragment fragment) {
            mWeakReferencesFragment = new WeakReference<>(fragment);
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

            mWeakReferencesFragment.get().onDisplayList(areas);
        }
    }
}
