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

import java.util.List;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.ui.RecipeDetailActivity;
import br.com.udacity.bakingtime.ui.RecipeDetailFragment;

public class RecipesRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder> {

    private final AppCompatActivity mParentActivity;
    private final Recipe[] mRecipes;
    private final boolean mTwoPane;

    public RecipesRecyclerViewAdapter(boolean twoPane, Recipe[] items, AppCompatActivity parent) {
        mTwoPane = twoPane;
        mRecipes = items;
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
        holder.mIdView.setText(mRecipes[position].getName());
        holder.mContentView.setText(mRecipes[position].describeContents());

        holder.itemView.setTag(mRecipes[position]);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mRecipes.length;
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
            if (mTwoPane) {

                Bundle arguments = new Bundle();
                arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, recipe.getName());

                RecipeDetailFragment fragment = new RecipeDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Class destinationClass = RecipeDetailActivity.class;
                Intent intent = new Intent(context, destinationClass);

                intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID,  recipe.getName());
                intent.putExtra(context.getString(R.string.recipe_steps_intent), recipe);
                context.startActivity(intent);

            }
        }
    };
}
