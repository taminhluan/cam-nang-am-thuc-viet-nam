package com.example.recipes.app.drawer;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.recipes.R;
import com.example.recipes.admin.dashboard.DashboardActivity;
import com.example.recipes.app.about.AboutDialogFragment;
import com.example.recipes.app.area.AreaActivity;
import com.example.recipes.app.category.CategoryActivity;
import com.example.recipes.app.event.EventActivity;
import com.example.recipes.app.main.MainActivity;

public class DrawerFragment extends Fragment implements View.OnClickListener {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private LinearLayout mMenuCategory;
    private LinearLayout mMenuArea;
    private LinearLayout mMenuEvent;
    private LinearLayout mMenuAbout;
    private LinearLayout mMenuHome;
    private LinearLayout mMenuAdmin;

    public DrawerFragment() {
        // Required empty public constructor
    }

    public static DrawerFragment newInstance() {
        DrawerFragment fragment = new DrawerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_frag, container, false);

        mapping(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void mapping(View view) {
        mMenuCategory = view.findViewById(R.id.menuCategory);
        mMenuArea = view.findViewById(R.id.menuArea);
        mMenuEvent = view.findViewById(R.id.menuEvent);
        mMenuAbout = view.findViewById(R.id.menuAbout);
        mMenuHome = view.findViewById(R.id.menuHome);
        mMenuAdmin = view.findViewById(R.id.menuAdmin);

        mMenuCategory.setOnClickListener(this);
        mMenuArea.setOnClickListener(this);
        mMenuEvent.setOnClickListener(this);
        mMenuAbout.setOnClickListener(this);
        mMenuHome.setOnClickListener(this);
        mMenuAdmin.setOnClickListener(this);
    }

    public void setUpDrawer(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                // Do something of Slide of Drawer
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.menuHome:
                intent = new Intent(this.getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuCategory:
                intent = new Intent(this.getActivity(), CategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.menuArea:
                intent = new Intent(this.getActivity(), AreaActivity.class);
                startActivity(intent);
                break;
            case R.id.menuEvent:
                intent = new Intent(this.getActivity(), EventActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAdmin:
                intent = new Intent(this.getActivity(), DashboardActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAbout:
                aboutDialog();
                break;
        }
    }
    private void aboutDialog() {
        new AboutDialogFragment().show(getActivity().getSupportFragmentManager(), null);
    }
}