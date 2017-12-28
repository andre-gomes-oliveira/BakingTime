package br.com.udacity.bakingtime.ui;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.adapters.StepsRecyclerViewAdapter;
import br.com.udacity.bakingtime.model.Ingredient;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.utilities.RecipesService;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is contained in a {@link RecipeStepsActivity}
 * which is only shown in handsets.
 */
public class RecipeStepsFragment extends Fragment {

    /**
     * The Recipe this fragment is presenting.
     */
    private Recipe mRecipe;

    /**
     * TYextView that will display the list of ingredients for the recipe.
     */
    @BindView(R.id.ingredients_tv) TextView mIngredientsTextView;

    /**
     * RecyclerView used to display the steps of the recipe
     */
    @BindView(R.id.step_list) RecyclerView mStepsRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeStepsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if(savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(getString(R.string.intent_recipe));
        }
        else if(getArguments().containsKey(getString(R.string.intent_recipe))){
            mRecipe = arguments.getParcelable(getString(R.string.intent_recipe));
        }

        if(mRecipe != null){
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
        Context context = this.getContext();

        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        ButterKnife.bind(this, rootView);

        if (mRecipe != null) {
            Ingredient[] ingredients = mRecipe.getIngredients();

            for( Ingredient ingredient : ingredients) {
                String name = ingredient.getName() + "\n";

                String measure = context.getString(R.string.ingredient_measure)
                        +  ingredient.getMeasure() + "\n";

                String quantity = context.getString(R.string.ingredient_quantity)
                        +  ingredient.getQuantity() + "\n";

                mIngredientsTextView.append(name + measure + quantity + "\n");
            }

            setupRecyclerView(mStepsRecyclerView, mRecipe);
            RecipesService.startActionUpdateRecipeWidgets(context, mRecipe);

            /*
      Layout manager used by the steps recycler view.
     */
            LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(this.getContext());
            mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
        }

        return rootView;
    }

    /**
     * Saving the recipe to be able to restore the state of the fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.intent_recipe), mRecipe);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, Recipe recipe) {
        Configuration config = getResources().getConfiguration();
        recyclerView.setAdapter(new StepsRecyclerViewAdapter(
                config.smallestScreenWidthDp >= 600, recipe, this.getActivity()));
    }
}
