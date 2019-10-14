package com.example.recipes.admin.event;


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
import com.example.recipes.admin.category.CategoryAdminDetailFragment;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;

public class EventAdminDetailFragment extends BaseFragment implements IEventAdminDetailFragment, View.OnClickListener {
    private Event mEvent;

    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    private EditText mEtId;
    private EditText mEtImage;
    private EditText mEtName;

    private LinearLayout mLlUpdateGroupButtons;
    private LinearLayout mLlAddGroupButtons;

    private ImageView mIvImage;
    public EventAdminDetailFragment() {
        // Required empty public constructor
    }

    public EventAdminDetailFragment(Event event) {
        mEvent = event;
    }

    public static EventAdminDetailFragment newInstance() {
        return new EventAdminDetailFragment();
    }

    public static EventAdminDetailFragment newInstance(Event event) {
        return new EventAdminDetailFragment(event);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.event_admin_detail_frag, container, false);
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
        display(mEvent);
    }

    private void hideGroupButton() {
        if (mEvent.getUid() == 0) { //CREATE => hide update button
            mLlUpdateGroupButtons.setVisibility(View.GONE);
        } else {
            mLlAddGroupButtons.setVisibility(View.GONE);
        }
    }

    @Override
    public void display(Event event) {
        mEtId.setText(event.getUid());
        mEtImage.setText(event.getImage());
        mEtName.setText(event.getName());
    }

    @Override
    public void add(Event event) {
        new AddAsyncTask().execute();
    }

    @Override
    public void update(Event event) {
        new UpdateAsyncTask().execute();
    }

    @Override
    public void delete(Event event) {
        new DeleteAsyncTask().execute();
    }

    @Override
    public void back() {
        getActivityNonNull().finish();
    }
    @Override
    public void onClick(View view) {
        if (view == mBtnAdd) {
            add(mEvent);
        } else if (view == mBtnDelete) {
            delete(mEvent);
        } else if (view == mBtnUpdate) {
            update(mEvent);
        }
    }

    private class AddAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getEventDao().insertAll(mEvent);
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

            db.getEventDao().updateAll(mEvent);

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

            db.getEventDao().delete(mEvent);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            back();
        }
    }
}
