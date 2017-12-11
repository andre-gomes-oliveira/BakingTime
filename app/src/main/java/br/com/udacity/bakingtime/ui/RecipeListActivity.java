package br.com.udacity.bakingtime.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.adapters.SimpleItemRecyclerViewAdapter;
import br.com.udacity.bakingtime.dummy.DummyContent;

import java.util.List;

/**
 * An activity representing a list of Recipes. The activity presents a list of items,
 * which when touched, lead to a {@link RecipeDetailActivity} representing item details.
 *
 * On tablet-size devices, item details are presented side-by-side with a list of items
 * in a {@link RecipeDetailActivity}.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Recycler view used to display a list of Recipes.
     */
    private RecyclerView mRecipesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecipesRecyclerView = findViewById(R.id.recipe_list);
        assert mRecipesRecyclerView != null;
        setupRecyclerView(mRecipesRecyclerView);

        handleIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }

    private void handleIntent() {
        //TODO: Handle this URI to fetch the recipes
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(false, DummyContent.ITEMS, this));
    }
}
