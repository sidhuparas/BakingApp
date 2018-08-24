package com.parassidhu.bakingapp.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Ingredients;
import com.parassidhu.bakingapp.model.ListItem;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.ui.RecipeListAdapter;
import com.parassidhu.bakingapp.ui.detail.StepsActivity;
import com.parassidhu.bakingapp.utils.Constants;
import com.parassidhu.bakingapp.utils.ItemClickSupport;
import com.parassidhu.bakingapp.viewmodel.MainViewModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeFragment extends Fragment {

    @BindView(R.id.recipe_list) RecyclerView mRecipeList;

    private OnFragmentInteractionListener mListener;
    private Context context;
    private RecipeListAdapter adapter;

    private List<List<Steps>> stepsList = new ArrayList<>();
    private List<List<Ingredients>> ingredientsList = new ArrayList<>();
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
                Intent intent = new Intent(getActivity(), StepsActivity.class);
                intent.putParcelableArrayListExtra(Constants.STEPS, new ArrayList<Parcelable>(stepsList.get(position)));
                intent.putParcelableArrayListExtra(Constants.INGREDIENTS, new ArrayList<Parcelable>(ingredientsList.get(position)));
                startActivity(intent);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
