package br.com.udacity.bakingtime.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.utilities.IngredientsService;
import br.com.udacity.bakingtime.utilities.RecipesService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, ArrayList<String> ingredients) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        //The recipe's title
        views.setTextViewText(R.id.appwidget_title, ((recipeName == null || recipeName.isEmpty())
                ? "" : recipeName));

        //The list of ingredients
        Intent serviceIntent = new Intent(context, IngredientsService.class);
        serviceIntent.putStringArrayListExtra(
                context.getString(R.string.service_recipe_ingredients), ingredients);
        views.setRemoteAdapter(R.id.appwidget_ingredient_list, serviceIntent);

        //The pending intent that launches the app if no recipe is being displayed
        Intent activityIntent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 ,
                activityIntent, 0);
        views.setOnClickPendingIntent(R.id.empty_view, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        RecipesService.startActionUpdateRecipeWidgets(context, null);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, String recipeName, ArrayList<String> ingredients) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, ingredients);
        }
    }
}

