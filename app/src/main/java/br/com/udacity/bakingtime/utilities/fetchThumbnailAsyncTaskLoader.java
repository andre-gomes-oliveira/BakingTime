package br.com.udacity.bakingtime.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import br.com.udacity.bakingtime.R;
import timber.log.Timber;


public class fetchThumbnailAsyncTaskLoader extends AsyncTaskLoader<Bitmap> {

    private final Bundle mBundle;

    public fetchThumbnailAsyncTaskLoader(@NonNull Context context, @NonNull final Bundle args) {
        super(context);
        this.mBundle = args;
    }

    @Override
    protected void onStartLoading() {
        if (mBundle == null) {
            return;
        }

        forceLoad();
    }

    @Nullable
    @Override
    public Bitmap loadInBackground() {
        Context context = getContext();
        Bitmap result;
        try {
            result =  Picasso.with(getContext())
                    .load(mBundle.getString(context.getString(R.string.player_thumbnail_uri)))
                    .placeholder(R.drawable.ic_cake_black_24dp)
                    .error(R.drawable.ic_error_outline_black_24dp)
                    .get();

        } catch (IOException e) {
            Timber.e(e.getMessage());
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
