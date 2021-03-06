package com.parassidhu.bakingapp.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Ingredients;
import com.parassidhu.bakingapp.model.ListItem;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.ui.adapter.RecipeListAdapter;
import com.parassidhu.bakingapp.ui.detail.StepListActivity;
import com.parassidhu.bakingapp.utils.Constants;
import com.parassidhu.bakingapp.utils.ItemClickSupport;
import com.parassidhu.bakingapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeFragment extends Fragment {

    @BindView(R.id.recipe_list) RecyclerView mRecipeList;

    private Context context;
    private RecipeListAdapter adapter;

    private List<List<Steps>> stepsList = new ArrayList<>();
    private List<List<Ingredients>> ingredientsList = new ArrayList<>();
    private List<ListItem> listItems = new ArrayList<>();
    private MainViewModel viewModel;

    public RecipeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialSetup();
        setupViewModel();
        setRclListener();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getContentFromAPI().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> listItems) {
                if (listItems != null && listItems.size() != 0) {
                    adapter = new RecipeListAdapter(context, new ArrayList<>(listItems));
                    mRecipeList.setAdapter(adapter);

                    RecipeFragment.this.listItems.clear();
                    RecipeFragment.this.listItems.addAll(listItems);

                    stepsList.clear();
                    ingredientsList.clear();

                    for (int i=0; i<listItems.size();i++){
                        stepsList.add(listItems.get(i).getSteps());
                        ingredientsList.add(listItems.get(i).getIngredients());
                    }
                }
            }
        });
    }

    private void initialSetup() {
        context = getActivity();
        mRecipeList.setLayoutManager(new GridLayoutManager(context, 2));
    }

    private void setRclListener(){
        ItemClickSupport.addTo(mRecipeList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), StepListActivity.class);
                intent.putParcelableArrayListExtra(Constants.STEPS,
                        new ArrayList<Parcelable>(stepsList.get(position)));
                intent.putParcelableArrayListExtra(Constants.INGREDIENTS,
                        new ArrayList<Parcelable>(ingredientsList.get(position)));

                savePreferenceForWidget(
                        ingredientsList.get(position),
                        listItems.get(position).getName()
                );

                intent.putExtra(Constants.RECIPE_NAME, listItems.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void savePreferenceForWidget(List<Ingredients> list, String name) {
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.WIDGET, Context.MODE_PRIVATE);

        StringBuilder ingredientsString = new StringBuilder();
        makeIngredientsString(list, ingredientsString);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.INGREDIENTS, ingredientsString.toString());
        editor.putString(Constants.RECIPE_NAME, name);
        editor.apply();
    }

    private void makeIngredientsString(List<Ingredients> list, StringBuilder ingredientsString ) {
        for (int i=0; i<list.size();i++){
            String measure = list.get(i).getMeasure();
            String ingredient = list.get(i).getIngredient();
            float quantity = list.get(i).getQuantity();

            ingredientsString
                    .append(quantity)
                    .append(" ")
                    .append(measure)
                    .append(" ")
                    .append(ingredient)
                    .append("\n");
        }
    }
}
