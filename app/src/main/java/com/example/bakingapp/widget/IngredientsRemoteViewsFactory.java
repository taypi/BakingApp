package com.example.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientsRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        updateIngredients();
    }

    @Override
    public void onDataSetChanged() {
        updateIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null || mIngredients.size() == 0) {
            return null;
        }

        Ingredient ingredient = mIngredients.get(position);
        double quantity = ingredient.getQuantity();
        String measure = ingredient.getMeasure();
        String ingredientName = ingredient.getName();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);

        views.setTextViewText(R.id.tv_ingredient_widget, ingredientName);
        views.setTextViewText(R.id.tv_quantity_widget, mContext.getResources()
                .getString(R.string.widget_quantity, quantity, measure));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void updateIngredients() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String jsonIngredients = sharedPreferences.getString(RecipeWidgetProvider.RECIPE_INGREDIENTS_KEY, "");
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();

        mIngredients = JsonUtils.fromJson(jsonIngredients, listType);
    }
}
