package br.com.udacity.bakingtime.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import timber.log.Timber;

public final class NetworkUtilities
{
    private static final String TAG = NetworkUtilities.class.getSimpleName();

    /* Base URLs*/
    private static final String BASE_RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildRecipesUrl(String request)
    {
        Uri builtUri;

        if(request.isEmpty())
            builtUri = Uri.parse(BASE_RECIPES_URL).buildUpon().build();
        else
            builtUri = Uri.parse(request).buildUpon().build();

        URL url = null;
        try
        {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        Timber.d("Built URL: %s", url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try
        {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
                return scanner.next();
            else
                return null;
        }
        finally
        {
            urlConnection.disconnect();
        }
    }
}
