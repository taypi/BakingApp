package com.example.bakingapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utils.IntentUtils;
import com.example.bakingapp.utils.JsonUtils;
import com.example.bakingapp.view.adapters.RecipesAdapter;
import com.example.bakingapp.viewmodel.RecipesViewModel;
import com.example.bakingapp.widget.RecipeWidgetProvider;

import java.io.IOException;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecipesAdapter mAdapter;
    private RecipesViewModel mViewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        mViewModel.getRecipes().observe(this, this::onRecipesChanged);

        mRecyclerView = findViewById(R.id.rv_recipes);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        configureRecyclerView();

        mSwipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void onRecipesChanged(List<Recipe> recipes) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setRecipes(recipes);
    }

    private void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mViewModel.loadRecipes();
    }

    private void onItemClicked(Recipe recipe) {
        IntentUtils.startActivity(this, RecipeDetailsActivity.class, recipe);
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new RecipesAdapter(this, this::onItemClicked);
        mRecyclerView.setAdapter(mAdapter);
    }
}
