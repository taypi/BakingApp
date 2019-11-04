package com.example.bakingapp.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.utils.IntentUtils;

public class StepDetailsViewModel extends AndroidViewModel {

    private Recipe mRecipe;
    private MutableLiveData<Step> mCurrentStep;

    public StepDetailsViewModel(@NonNull Application application) {
        super(application);
        mCurrentStep = new MutableLiveData<>();
    }

    public Recipe getRecipe(Activity activity) {
        if (mRecipe == null) {
            mRecipe = (Recipe) IntentUtils.getParcelableExtra(activity, R.string.error_get_recipe);
        }
        return mRecipe;
    }

    public Step getStep(int index) {
        if (mRecipe == null) {
            return null;
        }
        return mRecipe.getSteps().get(index);
    }

    public int getTotalSteps() {
        return mRecipe.getSteps().size();
    }

    public String getShortDescription(int index) {
        return getStep(index).getShortDescription();
    }

    public String getDescription(int index) {
        return getStep(index).getDescription();
    }

    public void setCurrentStep(int index) {
        mCurrentStep.postValue(getStep(index));
    }

    public LiveData<Step> getCurrentStep() {
        return mCurrentStep;
    }

    @SuppressLint("DefaultLocale")
    public String getProgressText(int index) {
        return String.format("%d of %d", index + 1, getTotalSteps());
    }

    public int getProgressPercentage(int index) {
        return (int) Math.round(100.0 * (index + 1) / getTotalSteps());
    }
}
