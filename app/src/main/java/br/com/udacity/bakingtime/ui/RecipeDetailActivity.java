package br.com.udacity.bakingtime.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a list of recipe steps. This activity
 * has different presentations for handset and tablet-size devices.
 * On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeDetailActivity extends AppCompatActivity
{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /**
     * The recipe that will have it's contents displayed by this activity
     */
    private Recipe mRecipe;

    /**
     * The id of the step that is being displayed
     */
    private int mStepIndex;

    /**
     * The activity's toolbar
     */
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.floating_back_button)
    FloatingActionButton mBackButton;

    @BindView(R.id.floating_forward_button)
    FloatingActionButton mForwardButton;

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

            mRecipe = getIntent().getParcelableExtra(getString(R.string.intent_recipe));
            Step step = getIntent().getParcelableExtra(getString(R.string.intent_recipe_step));

            if (step == null) {
                step = mRecipe.getSteps()[0];
            }

            mStepIndex = step.getId();

            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.intent_recipe), mRecipe);
            arguments.putParcelable(getString(R.string.intent_recipe_step), step);

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
        } else {
            mStepIndex = savedInstanceState.getInt(getString(R.string.intent_recipe_step_index));
            mRecipe = savedInstanceState.getParcelable(getString(R.string.intent_recipe));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.intent_recipe_step_index), mStepIndex);
        outState.putParcelable(getString(R.string.intent_recipe), mRecipe);
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

    /**
     * OnClick method for the back button, request a change to the previous step.
     */
    @OnClick(R.id.floating_back_button)
    public void onBackButtonClicked() {
        onFloatingButtonClicked(false);
    }

    /**
     * OnClick method for the back button, request a change to the next step.
     */
    @OnClick(R.id.floating_forward_button)
    public void onForwardButtonClicked() {
        onFloatingButtonClicked(true);
    }

    public void onFloatingButtonClicked(boolean forward) {

        Step[] steps = mRecipe.getSteps();
        if (forward) {
            if (mStepIndex < (steps.length -1)) {
                mStepIndex++;
            }
            else {
                Toast.makeText(this, getString(R.string.step_navigation_forward_error),
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (mStepIndex > 0) {
                mStepIndex--;
            }
            else {
                Toast.makeText(this, getString(R.string.step_navigation_backward_error),
                        Toast.LENGTH_SHORT).show();
            }
        }

        Bundle arguments = new Bundle();
        arguments.putParcelable(getString(R.string.intent_recipe), mRecipe);
        arguments.putParcelable(getString(R.string.intent_recipe_step), steps[mStepIndex]);

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();
    }
}
