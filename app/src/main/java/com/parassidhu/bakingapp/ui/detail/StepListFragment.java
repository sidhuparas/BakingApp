package com.parassidhu.bakingapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Ingredients;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.ui.IngredientsAdapter;
import com.parassidhu.bakingapp.ui.StepsAdapter;
import com.parassidhu.bakingapp.utils.Constants;
import com.parassidhu.bakingapp.utils.ItemClickSupport;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListFragment extends Fragment {

    @BindView(R.id.ingredientsList) RecyclerView ingredientsRcl;
    @BindView(R.id.stepsList) RecyclerView stepsRcl;

    private ArrayList<Steps> stepsList = new ArrayList<>();
    private ArrayList<Ingredients> ingredientsList = new ArrayList<>();

    public StepListFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, view);
        readBundle(getArguments());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setListener();
    }

    private void setListener() {
        ItemClickSupport.addTo(stepsRcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), StepDetailActivity.class);
                intent.putExtra(Constants.STEPS, stepsList);

                startActivity(intent);
            }
        });
    }

    private void setupViews() {
        ingredientsRcl.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        IngredientsAdapter adapter = new IngredientsAdapter(getActivity(), ingredientsList);
        ingredientsRcl.setAdapter(adapter);

        stepsRcl.setLayoutManager(new LinearLayoutManager(getActivity()));

        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(), stepsList);
        stepsRcl.setAdapter(stepsAdapter);
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            stepsList = bundle.getParcelableArrayList(Constants.STEPS);
            ingredientsList = bundle.getParcelableArrayList(Constants.INGREDIENTS);
        }
    }

    public static StepListFragment newInstance(ArrayList<Parcelable> steps,
                                               ArrayList<Parcelable> ingredients) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.STEPS, steps);
        bundle.putParcelableArrayList(Constants.INGREDIENTS, ingredients);
        StepListFragment fragment = new StepListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
