package br.com.udacity.bakingtime.model;

public class Recipe {

    private final int mId;
    private final String mName;
    private final Ingredient[] mIngredients;
    private final Step[] mSteps;
    private final int mServings;
    private final String mImageUrl;

    public Recipe(int id,
                  String name,
                  Ingredient[] ingredients,
                  Step[] steps,
                  int servings,
                  String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mServings = servings;
        this.mImageUrl = imageUrl;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Ingredient[] getIngredients() {
        return mIngredients;
    }

    public Step[] getSteps() {
        return mSteps;
    }

    public int getServings() {
        return mServings;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
