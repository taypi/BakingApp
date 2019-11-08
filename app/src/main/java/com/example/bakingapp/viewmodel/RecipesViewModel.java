package com.example.bakingapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipesViewModel extends AndroidViewModel implements Repository.RequestCallbacks {

    private final Repository mRepository;
    private MutableLiveData<List<Recipe>> mRecipes;

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        mRecipes = new MutableLiveData<>();
        mRepository = Repository.getInstance();
        loadRecipes();
    }

    @Override
    public void onSuccess(List<Recipe> recipes) {
        mRecipes.postValue(recipes);
    }

    @Override
    public void onFailure(@NonNull Throwable throwable) {
        mRecipes.postValue(new ArrayList<>());
        Log.d("Network error", Objects.requireNonNull(throwable.getMessage()));
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void loadRecipes() {
        mRepository.requestRecipes(this);
    }
}
