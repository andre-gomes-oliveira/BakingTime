package br.com.udacity.bakingtime.adapters;

import android.app.Activity;
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
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.model.Step;
import br.com.udacity.bakingtime.ui.RecipeDetailActivity;
import br.com.udacity.bakingtime.ui.RecipeDetailFragment;

public class StepsRecyclerViewAdapter
        extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private final AppCompatActivity mParentActivity;
    private final Recipe mRecipe;
    private final boolean mTwoPane;

    public StepsRecyclerViewAdapter(boolean twoPane, Recipe item, Activity parent) {
        mParentActivity = (AppCompatActivity) parent;
        mTwoPane = twoPane;
        mRecipe = item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Step step = mRecipe.getSteps()[position];

        holder.mShortDescView.setText(String.valueOf(step.getShortDesc()));
        holder.itemView.setTag(step);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return (mRecipe.getSteps().length);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mShortDescView;

        ViewHolder(View view) {
            super(view);
            mShortDescView = view.findViewById(R.id.short_desc_tv);
        }
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Step step = (Step) view.getTag();
            Context context = view.getContext();

            if (mTwoPane) {

                Bundle arguments = new Bundle();
                arguments.putParcelable(context.getString(R.string.intent_recipe), mRecipe);
                arguments.putParcelable(context.getString(R.string.intent_recipe_step), step);

                RecipeDetailFragment fragment = new RecipeDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else {

                Class destinationClass = RecipeDetailActivity.class;
                Intent intent = new Intent(context, destinationClass);

                intent.putExtra(context.getString(R.string.intent_recipe), mRecipe);
                intent.putExtra(context.getString(R.string.intent_recipe_step), step);
                context.startActivity(intent);
            }
        }
    };
}
