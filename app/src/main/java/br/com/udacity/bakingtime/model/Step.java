package br.com.udacity.bakingtime.model;

public class Step {

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
}
