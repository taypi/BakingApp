package com.example.bakingapp.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingapp.R;
import com.example.bakingapp.utils.IntentUtils;
import com.example.bakingapp.view.adapters.StepPagerAdapter;
import com.example.bakingapp.view.adapters.TabsPagerAdapter;
import com.example.bakingapp.view.fragments.StepsFragment;
import com.example.bakingapp.viewmodel.StepDetailsViewModel;
import com.google.android.material.tabs.TabLayout;

public class RecipeDetailsActivity extends AppCompatActivity {

    private StepDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mViewModel = ViewModelProviders.of(this).get(StepDetailsViewModel.class);

        if (findViewById(R.id.fl_steps) != null) {
            // Find the view pager that will allow the user to swipe between fragments
            ViewPager viewPager = findViewById(R.id.view_pager_steps);

            // Create an adapter that knows which fragment should be shown on each page
            StepPagerAdapter adapter = new StepPagerAdapter(getSupportFragmentManager(),
                    mViewModel.getRecipe(this).getSteps().size());

            // Set the adapter onto the view pager
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(IntentUtils.getIntExtra(this));

            // In two-pane mode, add initial StepsFragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            StepsFragment bodyFragment = new StepsFragment();
            bodyFragment.setOnClickHandler(viewPager::setCurrentItem);
            fragmentManager.beginTransaction()
                    .add(R.id.fl_steps, bodyFragment)
                    .commit();
        } else {
            // Find the view pager that will allow the user to swipe between fragments
            ViewPager viewPager = findViewById(R.id.view_pager_tabs);

            // Create an adapter that knows which fragment should be shown on each page
            TabsPagerAdapter adapter = new TabsPagerAdapter(this, getSupportFragmentManager());

            // Set the adapter onto the view pager
            viewPager.setAdapter(adapter);

            // Give the TabLayout the ViewPager
            TabLayout tabLayout = findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
