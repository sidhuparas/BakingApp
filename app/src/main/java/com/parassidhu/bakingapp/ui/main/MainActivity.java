package com.parassidhu.bakingapp.ui.main;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parassidhu.bakingapp.R;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener {

    private boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new RecipeFragment())
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("done", done);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
