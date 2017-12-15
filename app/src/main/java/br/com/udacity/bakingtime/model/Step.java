package br.com.udacity.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private final int mId;
    private final String mShortDesc;
    private final String mLongDesc;
    private final String mVideoUrl;
    private final String mPictureUrl;

    public Step(int id, String shortDesc, String longDesc, String videoUrl, String pictureUrl) {
        this.mId = id;
        this.mShortDesc = shortDesc;
        this.mLongDesc = longDesc;
        this.mVideoUrl = videoUrl;
        this.mPictureUrl = pictureUrl;
    }

    public Step(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mShortDesc = parcel.readString();
        this.mLongDesc = parcel.readString();
        this.mVideoUrl = parcel.readString();
        this.mPictureUrl = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mId);
        parcel.writeString(mShortDesc);
        parcel.writeString(mLongDesc);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mPictureUrl);
    }

    public int getId() {
        return mId;
    }

    public String getShortDesc() {
        return mShortDesc;
    }

    public String getLongDesc() {
        return mLongDesc;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {

        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int i) {
            return new Step[i];
        }
    };
}
