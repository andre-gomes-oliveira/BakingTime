package br.com.udacity.bakingtime.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Ingredient;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.model.Step;
import br.com.udacity.bakingtime.ui.RecipeDetailActivity;
import br.com.udacity.bakingtime.ui.RecipeDetailFragment;

public class StepsRecyclerViewAdapter
        extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private final AppCompatActivity mParentActivity;
    private final Ingredient[] mIngredients;
    private final Step[] mSteps;
    private final boolean mTwoPane;

    public StepsRecyclerViewAdapter(boolean twoPane, Recipe item, AppCompatActivity parent) {
        mTwoPane = twoPane;
        mIngredients = item.getIngredients();
        mSteps = item.getSteps();
        mParentActivity = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        // Including one extra item for the Ingredients
        return (mSteps.length + 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.id_text);
            mContentView = view.findViewById(R.id.content);
        }
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Recipe recipe = (Recipe) view.getTag();
            Context context = view.getContext();

            if (mTwoPane) {

                Bundle arguments = new Bundle();
                arguments.putParcelable(context.getString(R.string.recipe_steps_intent), recipe);

                RecipeDetailFragment fragment = new RecipeDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .commit();
            } else {

                //TODO: Verify how to adapt this logic to switch between multiple fragments
                Class destinationClass = RecipeDetailActivity.class;
                Intent intent = new Intent(context, destinationClass);

                intent.putExtra(context.getString(R.string.recipe_intent), recipe);
                context.startActivity(intent);
            }
        }
    };
}
