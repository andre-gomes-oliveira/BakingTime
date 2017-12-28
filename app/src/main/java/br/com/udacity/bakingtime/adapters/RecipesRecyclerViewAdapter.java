package br.com.udacity.bakingtime.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.ui.RecipeDetailActivity;
import br.com.udacity.bakingtime.ui.RecipeStepsActivity;
import br.com.udacity.bakingtime.utilities.RecipesService;

public class RecipesRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder> {

    private final AppCompatActivity mParentActivity;
    private final Recipe[] mRecipes;
    private final boolean mIsTablet;

    public RecipesRecyclerViewAdapter(boolean isTablet, Recipe[] items,  Activity parent) {
        mParentActivity = (AppCompatActivity) parent;
        mIsTablet = isTablet;
        mRecipes = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNameTextView.setText(mRecipes[position].getName());
        holder.mServingsTextView.setText(mParentActivity.getString(R.string.recipe_servings));
        holder.mServingsTextView.append(String.valueOf(mRecipes[position].getServings()));

        holder.itemView.setTag(mRecipes[position]);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mRecipes.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final CardView mCardView;
        final TextView mNameTextView;
        final TextView mServingsTextView;

        ViewHolder(View view) {
            super(view);
            mCardView = view.findViewById(R.id.recipe_card_view);
            mNameTextView = view.findViewById(R.id.name_text_view);
            mServingsTextView = view.findViewById(R.id.servings_text_view);
        }
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Recipe recipe = (Recipe) view.getTag();
            Context context = view.getContext();

            //Updates the widget when a recipe is selected
            RecipesService.startActionUpdateRecipeWidgets(context, recipe);

            if (mIsTablet) {
                Class destinationClass = RecipeDetailActivity.class;
                Intent intent = new Intent(context, destinationClass);

                intent.putExtra(context.getString(R.string.intent_recipe), recipe);
                context.startActivity(intent);
            } else {

                Class destinationClass = RecipeStepsActivity.class;
                Intent intent = new Intent(context, destinationClass);

                intent.putExtra(context.getString(R.string.intent_recipe), recipe);
                context.startActivity(intent);
            }
        }
    };
}
