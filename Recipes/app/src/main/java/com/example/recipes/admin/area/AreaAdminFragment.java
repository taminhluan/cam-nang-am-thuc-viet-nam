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
import android.widget.TextView;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.admin.category.CategoryAdminFragment;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    public void goToAddAreaFragment() {
        startActivity(new Intent(getActivityNonNull(), AreaAdminDetailActivity.class));
    }

    @Override
    public void displayList(List<Area> areas) {
        mRv.setAdapter(new AreasRecyclerViewAdapter(areas));
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            goToAddAreaFragment();
        }
    }

    public class AreasRecyclerViewAdapter extends RecyclerView.Adapter<AreaAdminFragment.AreasRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Area> data;

        public AreasRecyclerViewAdapter(List<Area> data) {
            this.data = data;
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
            Area area = data.get(i);
            recyclerViewHolder.mTvName.setText(area.getName());
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

            List<Area> all = db.getAreaDao().getAll();
            displayList(all);

            return null;
        }
    }
}
