package com.example.recipes.admin.category;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recipes.BaseFragment;
import com.example.recipes.R;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Category;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class CategoryAdminDetailFragment extends BaseFragment implements ICategoryAdminDetailFragment, View.OnClickListener {
    private Category mCategory;

    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    private EditText mEtId;
    private ImageButton mIbAddImage;
    private EditText mEtName;

    private LinearLayout mLlUpdateGroupButtons;
    private LinearLayout mLlAddGroupButtons;

    private ImageView mIvImage;

    public CategoryAdminDetailFragment() {
        mCategory = new Category();
    }

    public CategoryAdminDetailFragment(Category category) {
        mCategory = category;
    }

    public static CategoryAdminDetailFragment newInstance() {
        return new CategoryAdminDetailFragment();
    }

    public static CategoryAdminDetailFragment newInstance(Category category) {
        return new CategoryAdminDetailFragment(category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.category_admin_detail_frag, container, false);
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
        display(mCategory);
    }

    private void hideGroupButton() {
        if (mCategory.getUid() == 0) { //CREATE => hide update button
            mLlUpdateGroupButtons.setVisibility(View.GONE);
        } else {
            mLlAddGroupButtons.setVisibility(View.GONE);
        }
    }

    @Override
    public void display(Category category) {
        if (category == null) return;

        mEtId.setText(String.valueOf(category.getUid()));

        if (category.getImage() != null && !category.getImage().equals("")) {
            Picasso.get().load(category.getImage()).into(mIvImage);
        }
        mEtName.setText(category.getName());
    }

    @Override
    public void add(Category category) {
        obtainCategory();
        new AddAsyncTask(this).execute();
    }

    private void obtainCategory() {
        mCategory.setName(mEtName.getText().toString());
    }

    @Override
    public void update(Category category) {
        obtainCategory();
        new UpdateAsyncTask(this).execute();
    }

    @Override
    public void delete(Category category) {
        obtainCategory();
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
                            CategoryAdminDetailFragment.this.openCameraReturn(uriList);
                        }
                    });
        }
    }

    private void openCameraReturn(List<Uri> uriList) {
        if (uriList.size() > 0) {
            Uri uri = uriList.get(0);
            mCategory.setImage(uri.toString());
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
            add(mCategory);
        } else if (view == mBtnDelete) {
            delete(mCategory);
        } else if (view == mBtnUpdate) {
            update(mCategory);
        } else if (view == mIbAddImage) {
            openCamera();
        }
    }

    private class AddAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<ICategoryAdminDetailFragment> mWeakReferenceFragment;

        public AddAsyncTask(ICategoryAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getCategoryDao().insertAll(mCategory);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mWeakReferenceFragment.get().back();
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<ICategoryAdminDetailFragment> mWeakReferenceFragment;

        public UpdateAsyncTask(ICategoryAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getCategoryDao().updateAll(mCategory);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<ICategoryAdminDetailFragment> mWeakReferenceFragment;

        public DeleteAsyncTask(ICategoryAdminDetailFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            db.getCategoryDao().delete(mCategory);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mWeakReferenceFragment.get().back();
        }
    }
}
