package com.parassidhu.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private ArrayList<Steps> listItems;
    private Context context;

    public StepsAdapter(Context context, ArrayList<Steps> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_steps, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step) TextView stepTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(int position) {
            Steps step = listItems.get(position);
            stepTv.setText(step.getShortDescription());
        }
    }
}
