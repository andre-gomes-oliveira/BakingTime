package br.com.udacity.bakingtime.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;

public class RecipeStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        // Only create new fragments when there is no previously saved state
        if(savedInstanceState == null) {

            Recipe recipe = getIntent().getParcelableExtra(getString(R.string.recipe_intent));

            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.recipe_intent), recipe);

            RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
            stepsFragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.steps_container, stepsFragment)
                    .commit();
        }
    }
}
