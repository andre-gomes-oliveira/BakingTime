package br.com.udacity.bakingtime.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.udacity.bakingtime.model.Ingredient;
import br.com.udacity.bakingtime.model.Recipe;
import br.com.udacity.bakingtime.model.Step;

public class JsonUtilities {

    public static Recipe[] getRecipeDataFromJson(String recipesJsonStr)
            throws JSONException {

        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_IMAGE = "image";

        Recipe[] parsedRecipeData;

        // The JSON starts with an array
        JSONArray resultsArray = new JSONArray(recipesJsonStr);
        parsedRecipeData = new Recipe[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
            /* recipe data to be obtained*/
            int id;
            String name;
            Ingredient[] ingredients;
            Step[] steps;
            int servings;
            String imageUrl;

            /* Get the JSON object representing the movie */
            JSONObject recipe = resultsArray.getJSONObject(i);

            id = recipe.getInt(RECIPE_ID);
            name = recipe.getString(RECIPE_NAME);
            ingredients = getRecipeIngredientsFromJson(recipe.getJSONArray(RECIPE_INGREDIENTS));
            steps = getRecipeStepsFromJson(recipe.getJSONArray(RECIPE_STEPS));
            servings = recipe.getInt(RECIPE_SERVINGS);
            imageUrl = recipe.getString(RECIPE_IMAGE);

            parsedRecipeData[i] = new Recipe(id, name, ingredients, steps, servings, imageUrl);
        }

        return parsedRecipeData;
    }

    private static Ingredient[] getRecipeIngredientsFromJson(JSONArray ingredientsArray)
            throws JSONException {

        final String INGREDIENT_QUANTITY = "quantity";
        final String INGREDIENT_MEASURE = "measure";
        final String INGREDIENT_NAME = "ingredient";

        Ingredient[] parsedIngredients;
        parsedIngredients = new Ingredient[ingredientsArray.length()];

        for (int i = 0; i < ingredientsArray.length(); i++) {

            /* ingredient data to be obtained*/
            double quantity;
            String measure;
            String name;

            /* Get the JSON object representing the movie */
            JSONObject ingredient = ingredientsArray.getJSONObject(i);

            quantity = ingredient.getDouble(INGREDIENT_QUANTITY);
            measure = ingredient.getString(INGREDIENT_MEASURE);
            name = ingredient.getString(INGREDIENT_NAME);

            parsedIngredients[i] = new Ingredient(quantity, measure, name);
        }

        return parsedIngredients;
    }

    private static Step[] getRecipeStepsFromJson(JSONArray stepsArray)
            throws JSONException {

        final String STEP_ID = "id";
        final String STEP_SHORT_DESC = "shortDescription";
        final String STEP_LONG_DESC = "description";
        final String STEP_VIDEO_URL = "videoURL";
        final String STEP_PICTURE_URL = "thumbnailURL";

        Step[] parsedSteps;
        parsedSteps = new Step[stepsArray.length()];

        for (int i = 0; i < stepsArray.length(); i++) {

            /* step data to be obtained*/
            int id;
            String shortDesc;
            String longDesc;
            String videoUrl;
            String pictureUrl;

            /* Get the JSON object representing the movie */
            JSONObject step = stepsArray.getJSONObject(i);

            id = step.getInt(STEP_ID);
            shortDesc = step.getString(STEP_SHORT_DESC);
            longDesc = step.getString(STEP_LONG_DESC);
            videoUrl = step.getString(STEP_VIDEO_URL);
            pictureUrl = step.getString(STEP_PICTURE_URL);

            parsedSteps[i] = new Step(id, shortDesc, longDesc, videoUrl, pictureUrl);
        }

        return parsedSteps;
    }
}
