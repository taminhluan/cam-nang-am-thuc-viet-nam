package com.example.recipes.admin.area;


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
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;

import java.util.List;

public class AreaAdminDetailFragment extends BaseFragment implements IAreaAdminDetailFragment, View.OnClickListener {
    private Area mArea;

    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    private EditText mEtId;
    private EditText mEtImage;
    private EditText mEtName;

    private LinearLayout mLlUpdateGroupButtons;
    private LinearLayout mLlAddGroupButtons;

    private ImageView mIvImage;

    public AreaAdminDetailFragment() {
        mArea = new Area();
    }

    public AreaAdminDetailFragment(Area area) {
        mArea = area;
    }

    public static AreaAdminDetailFragment newInstance() {
        return new AreaAdminDetailFragment();
    }

    public static AreaAdminDetailFragment newInstance(Area area) {
        return new AreaAdminDetailFragment(area);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.area_admin_detail_frag, container, false);

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
        display(mArea);
    }

    private void hideGroupButton() {
        if (mArea.getUid() == 0) { //CREATE => hide update button
            mLlUpdateGroupButtons.setVisibility(View.GONE);
        } else {
            mLlAddGroupButtons.setVisibility(View.GONE);
        }
    }

    @Override
    public void display(Area area) {
        mEtId.setText(area.getUid());
        mEtImage.setText(area.getImage());
        mEtName.setText(area.getName());

        //TODO display image for ImageView
    }

    @Override
    public void add(Area area) {
        obtainValueFromScreen();
        new AddAsyncTask().execute();
    }

    @Override
    public void update(Area area) {
        obtainValueFromScreen();
        new UpdateAsyncTask().execute();
    }

    @Override
    public void delete(Area area) {
        obtainValueFromScreen();
        new DeleteAsyncTask().execute();
    }

    // get value from edittext
    // save to mArea
    private void obtainValueFromScreen() {
        mArea.setImage(mEtImage.getText().toString());
        mArea.setName(mEtName.getText().toString());
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            add(mArea);
        } else if (view == mBtnDelete) {
            delete(mArea);
        } else if (view == mBtnUpdate) {
            update(mArea);
        }
    }

    private class AddAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getAreaDao().insertAll(mArea);
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

            db.getAreaDao().updateAll(mArea);

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

            db.getAreaDao().delete(mArea);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            back();
        }
    }
}
