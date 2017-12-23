package br.com.udacity.bakingtime.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

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
     * The activity's toolbar
     */
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //Setting up ButterKnife
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Configuration config = getResources().getConfiguration();

        mTwoPane = config.smallestScreenWidthDp >= 600;

        if (savedInstanceState == null) {

            Recipe recipe = getIntent().getParcelableExtra(getString(R.string.recipe_intent));
            Step step = getIntent().getParcelableExtra(getString(R.string.recipe_step_intent));
            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.recipe_intent), recipe);
            arguments.putParcelable(getString(R.string.recipe_step_intent), step);

            if (mTwoPane) {
                // Create the step list fragment and add it to the activity
                // using a fragment transaction.
                RecipeStepsFragment fragment = new RecipeStepsFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_list_container, fragment)
                        .commit();
            }

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            if (mTwoPane) {
                navigateUpTo(new Intent(this, RecipeListActivity.class));
            } else {
                navigateUpTo(new Intent(this, RecipeStepsActivity.class));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
