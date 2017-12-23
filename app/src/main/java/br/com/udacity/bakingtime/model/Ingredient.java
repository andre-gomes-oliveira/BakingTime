package br.com.udacity.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private final double mQuantity;
    private final String mMeasure;
    private final String mName;

    public Ingredient(double quantity, String measure, String name) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mName = name;
    }

    private Ingredient(Parcel parcel) {
        this.mQuantity = parcel.readDouble();
        this.mMeasure = parcel.readString();
        this.mName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDouble(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mName);
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

    static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }
    };
}

