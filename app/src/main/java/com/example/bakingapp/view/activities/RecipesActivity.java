package com.example.bakingapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.view.adapters.RecipesAdapter;
import com.example.bakingapp.viewmodel.RecipesViewModel;

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
        Intent intentToStartDetailActivity = new Intent(this, RecipeDetailsActivity.class);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, recipe);
        startActivity(intentToStartDetailActivity);
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new RecipesAdapter(this, this::onItemClicked);
        mRecyclerView.setAdapter(mAdapter);
    }
}
