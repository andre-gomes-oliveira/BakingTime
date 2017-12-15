package br.com.udacity.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.dummy.DummyContent;
import br.com.udacity.bakingtime.adapters.RecipesRecyclerViewAdapter;

/**
 * An activity representing a list of recipe steps. This activity
 * has different presentations for handset and tablet-size devices.
 * On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /**
     *  Layout manager used by the steps recycler view.
     *  It will only be used in tablet devices.
     */
    private GridLayoutManager mStepsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.steps_list) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if (mTwoPane) {
            /*
      Recycler view used to display a list of recipe steps
       It will only be used in tablet devices.
     */
            RecyclerView mStepsRecyclerView = findViewById(R.id.steps_list);
            assert mStepsRecyclerView != null;
            setupRecyclerView(mStepsRecyclerView);

            mStepsLayoutManager = new GridLayoutManager(this, 1);
            mStepsRecyclerView.setLayoutManager(mStepsLayoutManager);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(RecipeDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
        else{
            if(mTwoPane){
                Parcelable recyclerLayoutState = savedInstanceState.getParcelable
                        (getString(R.string.bundle_recycler_position));

                if(recyclerLayoutState != null)
                    mStepsLayoutManager.onRestoreInstanceState(recyclerLayoutState);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        if(mTwoPane){
            outState.putParcelable(getString(R.string.bundle_recycler_position),
                    mStepsLayoutManager.onSaveInstanceState());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //TODO: Adjust the recyclerView to hold Recipe steps
        //recyclerView.setAdapter(new RecipesRecyclerViewAdapter(mTwoPane, DummyContent.ITEMS, this));
    }
}
