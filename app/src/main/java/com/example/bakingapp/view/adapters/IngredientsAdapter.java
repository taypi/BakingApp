package com.example.bakingapp.view.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.bakingapp.model.Ingredient} and makes a call to the
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> mIngredients;
    private final IngredientsAdapterOnClickHandler mClickHandler;

    public IngredientsAdapter(IngredientsAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredient item = mIngredients.get(position);
        holder.mName.setText(item.getName());
        holder.mQuantity.setText(getFormattedString(item));
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    public interface IngredientsAdapterOnClickHandler {
        void onClick(Ingredient ingredient);
    }

    @SuppressLint("DefaultLocale")
    private String getFormattedString(Ingredient ingredient) {
        String formattedString;
        double quantity = ingredient.getQuantity();
        if (quantity == (long) quantity) {
            formattedString = String.format("%d %s", (long) quantity, ingredient.getMeasure());
        } else {
            formattedString = String.format("%.2f %s", quantity, ingredient.getMeasure());
        }
        return formattedString;
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        final TextView mQuantity;
        final TextView mName;

        IngredientsViewHolder(View view) {
            super(view);
            mQuantity = view.findViewById(R.id.tv_ingredient_quantity);
            mName = view.findViewById(R.id.tv_ingredient_name);

            view.setOnClickListener(v -> mClickHandler.onClick(mIngredients.get(getAdapterPosition())));
        }
    }
}
