package com.example.bakingapp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.view.adapters.StepsAdapter;

import java.util.Objects;

public class StepsFragment extends Fragment {

    private Recipe mRecipe;

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
        handleIntentData();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        StepsAdapter adapter = new StepsAdapter(steps -> {});
        adapter.setSteps(mRecipe.getSteps());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void handleIntentData() {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        if (intent.hasExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE)
                && intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE) != null) {
            mRecipe = intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE);
        }
    }
}
