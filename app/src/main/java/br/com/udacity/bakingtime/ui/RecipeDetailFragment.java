package br.com.udacity.bakingtime.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is contained in a {@link RecipeDetailActivity}
 * either in two-pane mode (on tablets, side by side with a list of steps)
 * or a on handsets (by itself).
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The Recipe this fragment is presenting.
     */
    private Recipe mRecipe;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (getArguments().containsKey(getString(R.string.recipe_steps_intent))) {

            mRecipe = arguments.getParcelable(getString(R.string.recipe_steps_intent));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mRecipe.getName());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        if (mRecipe != null) {
            ((TextView) rootView.findViewById(R.id.step_description)).setText(mRecipe.getName());
        }

        return rootView;
    }

}