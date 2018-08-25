package com.parassidhu.bakingapp.ui.detail;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.utils.Constants;

import java.util.ArrayList;
import java.util.Optional;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<Parcelable> steps = new ArrayList<>();
        ArrayList<Parcelable> ingredients = new ArrayList<>();

        if (getIntent().getExtras()!=null){
            steps = getIntent().getParcelableArrayListExtra(Constants.STEPS);
            ingredients = getIntent().getParcelableArrayListExtra(Constants.INGREDIENTS);
            setTitle(getIntent().getStringExtra(Constants.RECIPE_NAME));
        }

        if (steps!=null && ingredients!=null) {
            Fragment fragment = StepListFragment.newInstance(steps,ingredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
