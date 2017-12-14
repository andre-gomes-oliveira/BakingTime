package br.com.udacity.bakingtime.data;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.utilities.JsonUtilities;
import br.com.udacity.bakingtime.utilities.NetworkUtilities;

public class RecipesLoader extends AsyncTaskLoader<Recipe[]> {

    private final Bundle mBundle;

    public RecipesLoader(@NonNull Context context, @NonNull final Bundle args) {
        super(context);
        mBundle = args;
    }

    @Override
    protected void onStartLoading()
    {
        if (mBundle == null) {
            return;
        }

        forceLoad();
    }

    @Nullable
    @Override
    public Recipe[] loadInBackground() {
        Context context = getContext();

        String request = mBundle.getString(context.getString(R.string.bundle_request));

        try
        {
            URL detailsUrl = NetworkUtilities.buildRecipesUrl(request != null ? request : "");

            String result = NetworkUtilities.getResponseFromHttpUrl(detailsUrl);
            return JsonUtilities.getRecipeDataFromJson(result);

        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
