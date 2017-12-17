package br.com.udacity.bakingtime.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.ui.RecipeDetailActivity;

public class RecipesRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder> {

    private final AppCompatActivity mParentActivity;
    private final Recipe[] mRecipes;

    public RecipesRecyclerViewAdapter(Recipe[] items, AppCompatActivity parent) {
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
        holder.mIdView.setText(String.valueOf(mRecipes[position].getId()));
        holder.mContentView.setText(mRecipes[position].getName());

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
            Context context = view.getContext();
            Class destinationClass = RecipeDetailActivity.class;
            Intent intent = new Intent(context, destinationClass);

            intent.putExtra(context.getString(R.string.recipe_intent), recipe);
            context.startActivity(intent);
        }
    };
}
