package com.example.recipes.admin.event;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.admin.category.CategoryAdminDetailFragment;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Category;
import com.example.recipes.model.Event;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class EventAdminDetailFragment extends BaseFragment implements IEventAdminDetailFragment, View.OnClickListener {
    private Event mEvent;

    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    private EditText mEtId;
    private EditText mEtName;

    private LinearLayout mLlUpdateGroupButtons;
    private LinearLayout mLlAddGroupButtons;

    private ImageButton mIbAddImage;

    private ImageView mIvImage;
    public EventAdminDetailFragment() {
        mEvent = new Event();
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
        mEtName = view.findViewById(R.id.etName);

        mBtnAdd = view.findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(this);
        mBtnUpdate = view.findViewById(R.id.btnUpdate);
        mBtnUpdate.setOnClickListener(this);
        mBtnDelete = view.findViewById(R.id.btnDelete);
        mBtnDelete.setOnClickListener(this);

        mLlUpdateGroupButtons = view.findViewById(R.id.llUpdateGroupButtons);
        mLlAddGroupButtons = view.findViewById(R.id.llCreateGroupButtons);

        mIbAddImage = view.findViewById(R.id.ibAddImage);
        mIbAddImage.setOnClickListener(this);

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
        mEtId.setText(String.valueOf(event.getUid()));
        if (event.getImage() != null && event.getImage() != "") {
            Picasso.get().load(event.getImage()).into(mIvImage);
        }
        mEtName.setText(event.getName());
    }

    @Override
    public void add(Event event) {
        obtainValueFromScreen();
        new AddAsyncTask(this).execute();
    }

    @Override
    public void update(Event event) {
        obtainValueFromScreen();
        new UpdateAsyncTask(this).execute();
    }

    @Override
    public void delete(Event event) {
        new DeleteAsyncTask(this).execute();
    }

    @Override
    public void openCamera() {
        if (isPermissionGranted()) {
            TedBottomPicker.with(getActivityNonNull())
                    .setPeekHeight(1600)
                    .showTitle(false)
                    .setCompleteButtonText(getText(R.string.btn_done).toString())
                    .setEmptySelectionText(getText(R.string.label_no_selected).toString())
                    .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                        @Override
                        public void onImagesSelected(List<Uri> uriList) {
                            EventAdminDetailFragment.this.openCameraReturn(uriList);
                        }
                    });
        }
    }

    private void openCameraReturn(List<Uri> uriList) {
        if (uriList.size() > 0) {
            Uri uri = uriList.get(0);
            mEvent.setImage(uri.toString());
            Picasso.get().load(uri.toString()).into(mIvImage);
        }
    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivityNonNull(), Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    == PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(getActivityNonNull(), Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED
            ) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivityNonNull(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        , Manifest.permission.CAMERA
                }, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
        } else if (view == mIbAddImage) {
            openCamera();
        }
    }

    private void obtainValueFromScreen() {
        //TODO fix
//        mEvent.setImage(mEtImage.getText().toString());
        mEvent.setName(mEtName.getText().toString());
    }

    private class AddAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<IEventAdminDetailFragment> mWeakReferenceFragment;
        public AddAsyncTask(IEventAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getEventDao().insertAll(mEvent);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<IEventAdminDetailFragment> mWeakReferenceFragment;
        public UpdateAsyncTask(IEventAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getEventDao().updateAll(mEvent);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<IEventAdminDetailFragment> mWeakReferenceFragment;
        public DeleteAsyncTask(IEventAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getEventDao().delete(mEvent);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }
}
