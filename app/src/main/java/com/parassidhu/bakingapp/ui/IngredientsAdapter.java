package com.parassidhu.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Ingredients;
import com.parassidhu.bakingapp.model.ListItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private ArrayList<Ingredients> listItems;
    private Context context;

    public IngredientsAdapter(Context context, ArrayList<Ingredients> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_ingredients, viewGroup, false);
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
        @BindView(R.id.quantity) TextView quantity;
        @BindView(R.id.desc) TextView description;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(int position) {
            Ingredients ingredient = listItems.get(position);
            quantity.setText(ingredient.getQuantity() + " " + ingredient.getMeasure());
            description.setText(ingredient.getIngredient());
        }
    }
}
