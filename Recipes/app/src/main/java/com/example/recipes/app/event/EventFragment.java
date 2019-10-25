package com.example.recipes.app.event;


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
import com.example.recipes.admin.event.EventAdminFragment;
import com.example.recipes.admin.event.IEventAdminFragment;
import com.example.recipes.app.recipe.RecipeActivity;
import com.example.recipes.constant.AppConstant;
import com.example.recipes.db.AppDatabase;
import com.example.recipes.model.Event;

import java.lang.ref.WeakReference;
import java.util.List;

public class EventFragment extends BaseFragment implements IEventFragment {

    private RecyclerView mRv;

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.event_frag, container, false);
        mapping(inflate);
        onGetList();
        return inflate;
    }

    private void mapping(View view) {
        mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(getActivityNonNull(), 2));
    }

    @Override
    public void onGetList() {
        new GetListAsyncTask(this).execute();
    }

    @Override
    public void onDisplayList(List<Event> events) {
        mRv.setAdapter(new EventsRecyclerViewAdapter(events, this));
    }

    @Override
    public void goToRecipesByEvent(Event event) {
        Intent intent = new Intent(getActivityNonNull(), RecipeActivity.class);
        intent.putExtra(AppConstant.EVENT, event);
        startActivity(intent);
    }

    public class EventsRecyclerViewAdapter extends RecyclerView.Adapter<EventsRecyclerViewAdapter.RecyclerViewHolder> {
        private List<Event> data;
        private IEventFragment mFragment;

        public EventsRecyclerViewAdapter(List<Event> data, IEventFragment fragment) {
            this.data = data;
            mFragment = fragment;
        }

        @NonNull
        @Override
        public EventsRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_event, viewGroup, false);
            return new EventsRecyclerViewAdapter.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventsRecyclerViewAdapter.RecyclerViewHolder recyclerViewHolder, int i) {
            final Event event = data.get(i);
            recyclerViewHolder.mTvName.setText(event.getName());
            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFragment.goToRecipesByEvent(event);
                }
            });
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
                mView = itemView;
                mTvName = itemView.findViewById(R.id.tvName);
            }
        }
    }

    private class GetListAsyncTask extends AsyncTask<Void, Void, List<Event>> {
        private WeakReference<IEventFragment> mWeakReferencesFragment;

        public GetListAsyncTask(IEventFragment fragment) {
            mWeakReferencesFragment = new WeakReference<>(fragment);
        }
        @Override
        protected List<Event> doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(getActivityNonNull());

            List<Event> all = db.getEventDao().getAll();

            return all;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            super.onPostExecute(events);

            mWeakReferencesFragment.get().onDisplayList(events);
        }
    }
}
