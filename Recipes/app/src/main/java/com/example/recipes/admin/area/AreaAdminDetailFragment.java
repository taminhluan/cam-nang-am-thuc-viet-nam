package com.example.recipes.admin.area;


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
import com.example.recipes.model.Area;
import com.example.recipes.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class AreaAdminDetailFragment extends BaseFragment implements IAreaAdminDetailFragment, View.OnClickListener {
    private Area mArea;

    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    private EditText mEtId;
    private EditText mEtName;

    private LinearLayout mLlUpdateGroupButtons;
    private LinearLayout mLlAddGroupButtons;

    private ImageButton mIbAddImage;
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

        mIbAddImage = view.findViewById(R.id.ibAddImage);

        mIbAddImage.setOnClickListener(this);
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
        mEtId.setText(String.valueOf(area.getUid()));
        if (area.getImage() != null && !area.getImage().equals("")) {
            Picasso.get().load(area.getImage()).into(mIvImage);
        }
        mEtName.setText(area.getName());
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
        //TODO Fix
//        mArea.setImage(mEtImage.getText().toString());
        mArea.setName(mEtName.getText().toString());
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
                            AreaAdminDetailFragment.this.openCameraReturn(uriList);
                        }
                    });
        }
    }

    private void openCameraReturn(List<Uri> uriList) {
        if (uriList.size() > 0) {
            Uri uri = uriList.get(0);
            mArea.setImage(uri.toString());
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
            add(mArea);
        } else if (view == mBtnDelete) {
            delete(mArea);
        } else if (view == mBtnUpdate) {
            update(mArea);
        } else if (view == mIbAddImage) {
            openCamera();
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
