package com.example.recipes.admin.area;


import android.app.PendingIntent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.admin.category.CategoryAdminFragment;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

public class AreaAdminFragment extends BaseFragment implements IAreaAdminFragment, View.OnClickListener {
    private FloatingActionButton mBtnAdd;
    private RecyclerView mRv;

    public AreaAdminFragment() {
        // Required empty public constructor
    }

    public static AreaAdminFragment newInstance() {
        return new AreaAdminFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.area_admin_frag, container, false);
        mapping(inflate);

        getList();

        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        getList();
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
        new GetListAsyncTask(this).execute();
    }

    @Override
    public void goToAddAreaFragment() {
        startActivity(new Intent(getActivityNonNull(), AreaAdminDetailActivity.class));
    }

    @Override
    public void goToUpdateAreaFragment(Area area) {
        Intent intent = new Intent(getActivityNonNull(), AreaAdminDetailActivity.class);
        intent.putExtra(AppConstant.AREA, area);
        startActivity(intent);
    }

    @Override
    public void displayList(List<Area> areas) {
        mRv.setAdapter(new AreasRecyclerViewAdapter(areas, this));
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            goToAddAreaFragment();
        }
    }

    public class AreasRecyclerViewAdapter extends RecyclerView.Adapter<AreaAdminFragment.AreasRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Area> data;
        private IAreaAdminFragment mFragment;

        public AreasRecyclerViewAdapter(List<Area> data, IAreaAdminFragment fragment) {
            this.data = data;
            mFragment = fragment;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_area, viewGroup, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            final Area area = data.get(i);
            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFragment.goToUpdateAreaFragment(area);
                }
            });
            recyclerViewHolder.mTvName.setText(area.getName());
            if (area.getImage() != null && !area.getImage().equals("")) {
                Picasso.get().load(area.getImage()).into(recyclerViewHolder.mIv);
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

    private class GetListAsyncTask extends AsyncTask<Void, Void, List<Area>> {
        private WeakReference<IAreaAdminFragment> mWeakReferencesFragment;

        public GetListAsyncTask(IAreaAdminFragment fragment) {
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

            mWeakReferencesFragment.get().displayList(areas);
        }
    }
}
