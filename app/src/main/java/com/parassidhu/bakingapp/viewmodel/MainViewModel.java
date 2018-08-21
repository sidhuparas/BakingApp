package com.parassidhu.bakingapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.parassidhu.bakingapp.model.ListItem;
import com.parassidhu.bakingapp.repo.RecipeRepo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private RecipeRepo recipeRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        recipeRepo = new RecipeRepo(application);
    }

    public MutableLiveData<List<ListItem>> getContentFromAPI(){ return recipeRepo.getContentFromAPI(); }
}
