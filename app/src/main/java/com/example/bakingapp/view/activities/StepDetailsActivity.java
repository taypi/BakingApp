package com.example.bakingapp.view.activities;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingapp.R;
import com.example.bakingapp.utils.IntentUtils;
import com.example.bakingapp.view.adapters.StepPagerAdapter;
import com.example.bakingapp.viewmodel.StepDetailsViewModel;

public class StepDetailsActivity extends AppCompatActivity {

    private StepDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        mViewModel = ViewModelProviders.of(this).get(StepDetailsViewModel.class);
        setTitle(mViewModel.getRecipe(this).getName());

        int currentItem = IntentUtils.getIntExtra(this);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.view_pager_steps);

        // Create an adapter that knows which fragment should be shown on each page
        StepPagerAdapter adapter = new StepPagerAdapter(getSupportFragmentManager(),
                mViewModel.getRecipe(this).getSteps().size());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(IntentUtils.getIntExtra(this));
        viewPager.addOnPageChangeListener(mPageChangeListener);

        setProgressBar(currentItem);
    }

    private void setProgressBar(int pos) {
        TextView stepCounter = findViewById(R.id.tv_step_counter);
        ProgressBar progressBar = findViewById(R.id.pb_steps);

        stepCounter.setText(mViewModel.getProgressText(pos));
        progressBar.setProgress(mViewModel.getProgressPercentage(pos));
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {}
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}
        @Override
        public void onPageSelected(int pos) {
            setProgressBar(pos);
            mViewModel.setCurrentStep(pos);
        }

    };
}
