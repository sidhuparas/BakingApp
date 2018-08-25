package com.parassidhu.bakingapp.ui.detail;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Ingredients;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.ui.main.RecipeFragment;
import com.parassidhu.bakingapp.utils.Constants;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ArrayList<Steps> stepsList = new ArrayList<>();

        if (getIntent() != null){
            stepsList = getIntent().getParcelableArrayListExtra(Constants.STEPS);
        }

        if (stepsList.size()!=0){
            Fragment fragment = StepDetailFragment.newInstance(stepsList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
