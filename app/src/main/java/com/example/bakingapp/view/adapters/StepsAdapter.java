package com.example.bakingapp.view.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Step;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.bakingapp.model.Step} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Step> mSteps;
    private final StepsAdapterOnClickHandler mClickHandler;

    public StepsAdapter(StepsAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    public void setSteps(List<Step> Steps) {
        mSteps = Steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step item = mSteps.get(position);
        holder.mShortDescription.setText(item.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.size();
    }

    public interface StepsAdapterOnClickHandler {
        void onClick(int position);
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        final TextView mShortDescription;

        StepsViewHolder(View view) {
            super(view);
            mShortDescription = view.findViewById(R.id.tv_step_short_description);

            view.setOnClickListener(v -> mClickHandler.onClick(getAdapterPosition()));
        }
    }
}
