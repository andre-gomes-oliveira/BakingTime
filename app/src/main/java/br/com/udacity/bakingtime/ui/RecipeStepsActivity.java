package br.com.udacity.bakingtime.ui;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsActivity extends AppCompatActivity {

    /**
     * The activity's Toolbar
     */
    @BindView(R.id.steps_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.steps_toolbar_layout)
    CollapsingToolbarLayout mTollBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        //Setting up ButterKnife
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            Recipe recipe = getIntent().getParcelableExtra(getString(R.string.intent_recipe));
            mTollBarLayout.setTitle(recipe.getName());

            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.intent_recipe), recipe);

            RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
            stepsFragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.steps_container, stepsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
