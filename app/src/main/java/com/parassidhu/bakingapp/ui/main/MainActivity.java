package com.parassidhu.bakingapp.ui.main;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parassidhu.bakingapp.R;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new RecipeFragment())
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
