package com.parassidhu.bakingapp.model;

import java.util.List;

public class ListItem {
    private int id;
    private String name;
    private List<Ingredients> ingredients;
    private List<Steps> steps;

    public ListItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
