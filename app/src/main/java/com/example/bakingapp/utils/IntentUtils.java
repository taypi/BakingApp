package com.example.bakingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.bakingapp.view.activities.RecipesActivity;

public class IntentUtils {

    public static Object getParcelableExtra(Activity activity, int errorMessageId) {
        Object extra = null;
        Intent intent = activity.getIntent();
        if (intent.hasExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE)
                && intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE) != null) {
            extra = intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE);
        } else {
            handleError(activity, errorMessageId);
        }
        return extra;
    }

    public static int getIntExtra(Activity activity) {
        return activity.getIntent().getIntExtra(Intent.EXTRA_INDEX, 0);
    }

    public static <T> void startActivity(Context context, Class<T> tClass, Parcelable extra) {
        Intent intentToStartActivity = new Intent(context, tClass);
        intentToStartActivity.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, extra);
        context.startActivity(intentToStartActivity);
    }

    public static <T> void startActivity(Context context, Class<T> tClass, Parcelable extra, int index) {
        Intent intentToStartActivity = new Intent(context, tClass);
        intentToStartActivity.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, extra);
        intentToStartActivity.putExtra(Intent.EXTRA_INDEX, index);
        context.startActivity(intentToStartActivity);
    }

    private static void handleError(Activity activity, int errorMessageId) {
        startActivity(activity, RecipesActivity.class, null);
        String errorMessage = activity.getResources().getString(errorMessageId);
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
    }
}
