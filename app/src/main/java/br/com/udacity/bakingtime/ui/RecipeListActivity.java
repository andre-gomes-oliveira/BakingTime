package br.com.udacity.bakingtime.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.adapters.SimpleItemRecyclerViewAdapter;
import br.com.udacity.bakingtime.dummy.DummyContent;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * An activity representing a list of Recipes. The activity presents a list of items,
 * which when touched, lead to a {@link RecipeDetailActivity} representing item details.
 * <p>
 * On tablet-size devices, item details are presented side-by-side with a list of items
 * in a {@link RecipeDetailActivity}.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Recycler view used to display a list of Recipes.
     */
    private RecyclerView mRecipesRecyclerView;

    /**
     *  Layout manager used by the recipes recycler view.
     *  It will contain just one column on phones, and three columns on tablet devices.
     */
    private GridLayoutManager mRecipesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecipesRecyclerView = findViewById(R.id.recipe_list);
        assert mRecipesRecyclerView != null;
        setupRecyclerView(mRecipesRecyclerView);

        Configuration config = getResources().getConfiguration();

        if (config.smallestScreenWidthDp >= 600){
            if(config.orientation == ORIENTATION_LANDSCAPE)
                mRecipesLayoutManager = new GridLayoutManager(this, 3);
            else
                mRecipesLayoutManager = new GridLayoutManager(this, 2);
        }
        else
            mRecipesLayoutManager = new GridLayoutManager(this, 1);

        if(savedInstanceState != null)
        {
            Parcelable recyclerLayoutState = savedInstanceState.getParcelable
                    (getString(R.string.bundle_recycler_position));

            if(recyclerLayoutState != null)
                mRecipesLayoutManager.onRestoreInstanceState(recyclerLayoutState);
        }
        else
            handleIntent();

        mRecipesRecyclerView.setLayoutManager(mRecipesLayoutManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
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

    private void handleIntent() {
        //TODO: Handle this URI to fetch the recipes
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(false, DummyContent.ITEMS, this));
    }
}
