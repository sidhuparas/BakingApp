package com.parassidhu.bakingapp.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.ListItem;
import com.parassidhu.bakingapp.ui.RecipeListAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepo {

    private final Context context;
    private MutableLiveData<List<ListItem>> listLiveData = new MutableLiveData<>();

    public RecipeRepo(Application application){
        this.context = application;
    }

    public LiveData<List<ListItem>> getContentFromAPI() {
        Log.d("Recipe", "getContentFromAPI: Called");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                context.getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    Gson gson = new Gson();
                    List<ListItem> listItems = gson.fromJson(jsonArray.toString(),
                            new TypeToken<List<ListItem>>(){}.getType());
                    listLiveData.postValue(listItems);

                }catch (Exception ignored){
                    Log.d("Recipe", "onResponse: Exception: " + ignored.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Recipe", "onErrorResponse: " + error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return listLiveData;
    }

}
