package br.com.udacity.bakingtime.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.adapters.RecipesRecyclerViewAdapter;
import br.com.udacity.bakingtime.data.RecipesLoader;
import br.com.udacity.bakingtime.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * An activity representing a list of Recipes. The activity presents a list of items,
 * which when touched, lead to a {@link RecipeDetailActivity} representing item details.
 * <p>
 * On tablet-size devices, item details are presented side-by-side with a list of items
 * in a {@link RecipeDetailActivity}.
 */
public class RecipeListActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Recipe[]> {

    /**
     * Layout manager used by the recipes recycler view.
     * It will contain just one column on phones, and three columns on tablet devices.
     */
    private GridLayoutManager mRecipesLayoutManager;

    /**
     Recycler view used to display a list of Recipes.
     */
    @BindView(R.id.recipe_list)
    RecyclerView mRecipesRecyclerView;

    /**
     The activity's Toolbar
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     TextView used to display error messages when the connection fails
     */
    @BindView(R.id.tv_error_message)
    TextView mErrorMessageDisplay;

    /**
     * Unique identifier for the loader used by this activity
     */
    private static final int RECIPES_LOADER = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //Setting up Timber
        Timber.plant(new Timber.DebugTree());

        //Setting up ButterKnife
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        Configuration config = getResources().getConfiguration();

        if (config.smallestScreenWidthDp >= 600) {
            if (config.orientation == ORIENTATION_LANDSCAPE)
                mRecipesLayoutManager = new GridLayoutManager(this, 3);
            else
                mRecipesLayoutManager = new GridLayoutManager(this, 2);
        } else
            mRecipesLayoutManager = new GridLayoutManager(this, 1);

        getSupportLoaderManager().initLoader(RECIPES_LOADER, null, this);

        if (savedInstanceState != null) {
            Parcelable recyclerLayoutState = savedInstanceState.getParcelable
                    (getString(R.string.bundle_recycler_position));

            if (recyclerLayoutState != null)
                mRecipesLayoutManager.onRestoreInstanceState(recyclerLayoutState);
        } else
            handleIntent();

        mRecipesRecyclerView.setLayoutManager(mRecipesLayoutManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(getString(R.string.bundle_recycler_position),
                mRecipesLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }

    @Override
    public Loader<Recipe[]> onCreateLoader(int id, Bundle args) {
        return new RecipesLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] data) {
        if ((data != null) && (data.length > 0)) {
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);
            setupRecyclerView(mRecipesRecyclerView, data);
        }
        else{
            setupRecyclerView(mRecipesRecyclerView, new Recipe[0]);
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
        }
    }

    /* This function had to be overridden, but it will not be used by this activity */
    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {

    }

    private void handleIntent() {

        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();

        LoaderManager loaderManager = getSupportLoaderManager();

        Bundle requestBundle = new Bundle();

        requestBundle.putString(getString(R.string.bundle_request),
                appLinkData != null ? appLinkData.toString() : null);

        Loader<String> recipesLoader = loaderManager.getLoader(RECIPES_LOADER);

        if (recipesLoader == null)
            loaderManager.initLoader(RECIPES_LOADER, requestBundle, this);
        else
            loaderManager.restartLoader(RECIPES_LOADER, requestBundle, this);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, Recipe[] recipes) {
        Configuration config = getResources().getConfiguration();
        recyclerView.setAdapter(new RecipesRecyclerViewAdapter(
                config.smallestScreenWidthDp >= 600, recipes, this));
    }
}
