package br.com.udacity.bakingtime.utilities;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Ingredient;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.ui.RecipeWidgetProvider;

public class RecipesService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE = "br.com.udacity.bakingtime.utilities.action.update_recipe";

    public RecipesService() {
        super(RecipesService.class.getSimpleName());
    }

    public RecipesService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Context context = getApplicationContext();
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE.equals(action)) {
                String recipeName = intent.getStringExtra(context.getString(R.string.service_recipe_name));
                ArrayList<String> ingredients = intent.getStringArrayListExtra(context.getString(R.string.service_recipe_ingredients));
                handleActionUpdateRecipe(recipeName, ingredients);
            }
        }
    }

    /**
     * Starts this service to update the widget with the most recent recipe.
     */
    public static void startActionUpdateRecipeWidgets(Context context, @Nullable Recipe recipe) {
        Intent intent = new Intent(context, RecipesService.class);

        if (recipe != null) {
            Ingredient[] ingredients = recipe.getIngredients();
            ArrayList<String> viewText = new ArrayList<>(ingredients.length);

            for (Ingredient ingredient : ingredients) {
                String description = ingredient.getName() +
                        "\n" +
                        context.getString(R.string.ingredient_measure) +
                        ingredient.getMeasure() +
                        "\n" +
                        context.getString(R.string.ingredient_quantity) +
                        ingredient.getQuantity();

                viewText.add(description);
            }

            intent.putExtra(context.getString(R.string.service_recipe_name), recipe.getName());
            intent.putStringArrayListExtra(context.getString(R.string.service_recipe_ingredients), viewText);
            intent.setAction(ACTION_UPDATE_RECIPE);
            context.startService(intent);
        }
    }

    private void handleActionUpdateRecipe(String recipeName, ArrayList<String> ingredients) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        //Now update all widgets
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds, recipeName, ingredients);
    }
}
