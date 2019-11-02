package com.example.bakingapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.view.adapters.TabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class RecipeDetailsActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        handleIntentData();

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Create an adapter that knows which fragment should be shown on each page
        TabsPagerAdapter adapter = new TabsPagerAdapter(this, getSupportFragmentManager(), mRecipe);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void handleIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE)
                && intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE) != null) {
            mRecipe = intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE);
        } else {
            startActivity(new Intent(this, RecipesActivity.class));

            String errorMessage = getResources().getString(R.string.detail_error);
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
