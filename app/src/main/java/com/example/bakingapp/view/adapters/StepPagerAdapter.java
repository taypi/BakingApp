package com.example.bakingapp.view.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bakingapp.view.fragments.StepDetailsFragment;

public class StepPagerAdapter extends FragmentPagerAdapter {

    private final int mTotalSteps;

    public StepPagerAdapter(@NonNull FragmentManager fm, int totalSteps) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mTotalSteps = totalSteps;
    }

    @NonNull
    @Override
    public Fragment getItem(int index) {
        // getItem is called to instantiate the fragment for the given page.
        return StepDetailsFragment.getInstance(index);
    }

    @Override
    public int getCount() {
        return mTotalSteps;
    }
}
