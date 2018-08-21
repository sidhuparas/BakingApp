package com.parassidhu.bakingapp.ui.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parassidhu.bakingapp.R;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new StepListFragment())
                .commit();
    }
}
