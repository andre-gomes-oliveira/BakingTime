package br.com.udacity.bakingtime.model;

public class Ingredient {

    private final double mQuantity;
    private final String mMeasure;
    private final String mName;

    public Ingredient(double quantity, String measure, String name) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mName = name;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getName() {
        return mName;
    }
}
