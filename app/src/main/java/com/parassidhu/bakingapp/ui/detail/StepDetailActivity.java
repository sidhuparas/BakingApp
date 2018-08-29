package com.parassidhu.bakingapp.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.utils.Constants;

import java.util.ArrayList;

import io.github.prototypez.savestate.core.annotation.AutoRestore;

public class StepDetailActivity extends AppCompatActivity {

    @AutoRestore
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ArrayList<Steps> stepsList = new ArrayList<>();

        if (getIntent() != null){
            stepsList = getIntent().getParcelableArrayListExtra(Constants.STEPS);
            position = getIntent().getIntExtra(Constants.POSITION, 0);
        }

        if (savedInstanceState == null) {
            if (stepsList.size() != 0) {
                Fragment fragment = StepDetailFragment.newInstance(stepsList, position);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Put random value as a temporary bug-fix to ViewModel bug in early v28 libraries
        outState.putLong(Constants.PLAYER_POSITION, 2);
    }
}
