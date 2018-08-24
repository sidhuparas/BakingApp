package com.parassidhu.bakingapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.parassidhu.bakingapp.model.ListItem;
import com.parassidhu.bakingapp.repo.RecipeRepo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private RecipeRepo recipeRepo;
    private LiveData<List<ListItem>> recipeData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        recipeRepo = new RecipeRepo(application);

        if (recipeData == null) {
            Log.d("Recipe", "getContentFromAPI: Called It's null");
            recipeData = recipeRepo.getContentFromAPI();
        }

    }

    public LiveData<List<ListItem>> getContentFromAPI(){ return recipeData; }
}
