package com.example.bakingapp.repository;

import androidx.annotation.NonNull;

import com.example.bakingapp.model.Recipe;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private final static String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static Repository sInstance;
    private final RecipeService mService;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(RecipeService.class);
    }

    synchronized public static Repository getInstance() {
        if (sInstance == null) {
            sInstance = new Repository();
        }
        return sInstance;
    }

    public void requestRecipes(@NonNull RequestCallbacks callbacks) {
        mExecutor.submit(() -> enqueueCall(mService.getRecipes(), callbacks));
    }

    private void enqueueCall(Call<List<Recipe>> call, @NonNull RequestCallbacks callbacks) {
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.body() != null) {
                    callbacks.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                callbacks.onFailure(t);
            }
        });
    }

    public interface RequestCallbacks {
        void onSuccess(List<Recipe> recipes);
        void onFailure(@NonNull Throwable throwable);
    }
}