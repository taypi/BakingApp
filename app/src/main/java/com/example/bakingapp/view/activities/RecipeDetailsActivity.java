package com.example.bakingapp.view.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utils.IntentUtils;
import com.example.bakingapp.utils.JsonUtils;
import com.example.bakingapp.view.adapters.StepPagerAdapter;
import com.example.bakingapp.view.adapters.TabsPagerAdapter;
import com.example.bakingapp.view.fragments.StepsFragment;
import com.example.bakingapp.viewmodel.StepDetailsViewModel;
import com.example.bakingapp.widget.RecipeWidgetProvider;
import com.google.android.material.snackbar.Snackbar;
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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_widget) {
            updateSharedPreference(mViewModel.getRecipe(this));
            sendBroadcastToWidget();
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.recipe_added_to_widget),
                    Snackbar.LENGTH_LONG)
                    .show();
        }
        return super.onOptionsItemSelected(item);
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

    private void sendBroadcastToWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        Intent updateAppWidgetIntent = new Intent();
        updateAppWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateAppWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(updateAppWidgetIntent);
    }

    private void updateSharedPreference(Recipe recipe) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String ingredients = JsonUtils.toJson(recipe.getIngredients());

        editor.putString(RecipeWidgetProvider.RECIPE_INGREDIENTS_KEY, ingredients);
        editor.putString(RecipeWidgetProvider.RECIPE_NAME_KEY, recipe.getName());

        editor.apply();
    }
}
