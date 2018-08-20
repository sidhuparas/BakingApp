package com.parassidhu.bakingapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parassidhu.bakingapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepListFragment extends Fragment {


    public StepListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_list, container, false);
    }

}
