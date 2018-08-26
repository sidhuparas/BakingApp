package com.parassidhu.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.ListItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private ArrayList<ListItem> listItems;
    private Context context;

    public RecipeListAdapter(Context context, ArrayList<ListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_recipe, viewGroup, false);
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
        @BindView(R.id.name) TextView name;
        @BindView(R.id.image) ImageView image;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(int position) {
            name.setText(listItems.get(position).getName());

            switch (position){
                case 0:
                    image.setImageResource(R.drawable.img_nutella_pie);
                    break;
                case 1:
                    image.setImageResource(R.drawable.img_brownies);
                    break;
                case 2:
                    image.setImageResource(R.drawable.img_yellow_cake);
                    break;
                case 3:
                    image.setImageResource(R.drawable.img_cheesecake);
                    break;
            }
        }
    }
}
