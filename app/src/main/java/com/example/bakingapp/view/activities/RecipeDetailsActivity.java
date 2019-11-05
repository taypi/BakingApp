package com.example.bakingapp.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
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
        Recipe recipe = mViewModel.getRecipe(this);

        setTitle(recipe.getName());

        if (isTwoPane()) {
            setTwoPaneLayout(recipe);
        } else {
            setOnePaneLayout();
        }
    }

    private boolean isTwoPane() {
        return findViewById(R.id.fl_steps) != null;
    }

    private void setTwoPaneLayout(Recipe recipe) {
        ViewPager viewPager = findViewById(R.id.view_pager_steps);
        StepPagerAdapter adapter = new StepPagerAdapter(getSupportFragmentManager(),
                recipe.getSteps().size());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(IntentUtils.getIntExtra(this));

        FragmentManager fragmentManager = getSupportFragmentManager();
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setOnClickHandler(viewPager::setCurrentItem);
        fragmentManager.beginTransaction()
                .add(R.id.fl_steps, stepsFragment)
                .commit();
    }

    private void setOnePaneLayout() {
        ViewPager viewPager = findViewById(R.id.view_pager_tabs);
        TabsPagerAdapter adapter = new TabsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
