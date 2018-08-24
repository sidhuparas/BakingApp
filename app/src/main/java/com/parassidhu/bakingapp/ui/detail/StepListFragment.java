package com.parassidhu.bakingapp.ui.detail;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Ingredients;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class StepListFragment extends Fragment {

    private ArrayList<Steps> stepsList = new ArrayList<>();
    private ArrayList<Ingredients> ingredientsList = new ArrayList<>();

    public StepListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, view);
        readBundle(getArguments());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("list", "onViewCreated: " + stepsList.get(0).getDescription());

    }

    private void readBundle(Bundle bundle){
        if (bundle!=null){
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
