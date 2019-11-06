package com.example.bakingapp.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utils.IntentUtils;
import com.example.bakingapp.view.adapters.IngredientsAdapter;
import com.example.bakingapp.view.adapters.StepsAdapter;

import java.util.Objects;

/**
 * A fragment representing a list of Ingredients.
 */
public class IngredientsFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        Recipe recipe = (Recipe) IntentUtils.getParcelableExtra(Objects.requireNonNull(getActivity()), R.string.error_get_recipe);
        setRecyclerView(view, recipe);
        return view;
    }

    private void setRecyclerView(View view, Recipe recipe) {
        RecyclerView recyclerView = (RecyclerView) view;
        IngredientsAdapter adapter = new IngredientsAdapter(ingredient -> {});
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);

        adapter.setIngredients(recipe.getIngredients());

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }
}
