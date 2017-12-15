package br.com.udacity.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{

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

    private Recipe(Parcel in)
    {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mIngredients = in.createTypedArray(Ingredient.CREATOR);
        this.mSteps = in.createTypedArray(Step.CREATOR);
        this.mServings = in.readInt();
        this.mImageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeTypedArray(mIngredients, flags);
        parcel.writeTypedArray(mSteps, flags);
        parcel.writeInt(mServings);
        parcel.writeString(mImageUrl);
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

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };
}
