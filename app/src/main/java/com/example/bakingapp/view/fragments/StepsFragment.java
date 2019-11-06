package com.example.bakingapp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utils.IntentUtils;
import com.example.bakingapp.view.activities.StepDetailsActivity;
import com.example.bakingapp.view.adapters.StepsAdapter;

import java.util.Objects;

public class StepsFragment extends Fragment {

    private Recipe mRecipe;
    private StepsAdapter.StepsAdapterOnClickHandler mOnClickHandler;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        mRecipe = (Recipe) IntentUtils.getParcelableExtra(Objects.requireNonNull(getActivity()), R.string.error_get_recipe);
        setRecyclerView(view);
        return view;
    }

    public void setOnClickHandler(StepsAdapter.StepsAdapterOnClickHandler handler) {
        mOnClickHandler = handler;
    }

    private void onItemClicked(int index) {
        if (mOnClickHandler != null) {
            mOnClickHandler.onClick(index);
        } else {
            IntentUtils.startActivity(getContext(), StepDetailsActivity.class, mRecipe, index);
        }
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view;
        StepsAdapter adapter = new StepsAdapter(this::onItemClicked);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);

        adapter.setSteps(mRecipe.getSteps());

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }
}
