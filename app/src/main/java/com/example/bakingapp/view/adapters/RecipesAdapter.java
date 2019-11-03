package com.example.bakingapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utils.ImageUtils;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private final RecipesAdapterOnClickHandler mClickHandler;
    private final Context mContext;
    private List<Recipe> mRecipes;

    public RecipesAdapter(Context context, RecipesAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.mName.setText(recipe.getName());
        ImageUtils.setImage(holder.mImage, recipe.getImage(), recipe.getName());
        ImageUtils.setBackgroundRandomColor(mContext, holder.mCardView);

    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder {
        final TextView mName;
        final ImageView mImage;
        final CardView mCardView;

        RecipesViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.tv_recipe_name);
            mImage = view.findViewById(R.id.iv_recipe);
            mCardView = view.findViewById(R.id.cv_recipe);

            view.setOnClickListener(v -> mClickHandler.onClick(mRecipes.get(getAdapterPosition())));
        }
    }
}
