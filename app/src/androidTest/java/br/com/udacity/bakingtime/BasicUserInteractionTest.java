package br.com.udacity.bakingtime;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.udacity.bakingtime.ui.RecipeListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Testing a few basic user interactions with recyclerviews
 * These tests are largely based on the examples found at this repository:
 * https://github.com/googlesamples/android-testing/tree/master/ui/espresso/RecyclerViewSample
 * <p>
 * Moreover, these tests are mostly focused on the fragments, since they are intended to work
 * on both handset and tablet devices
 */

@RunWith(AndroidJUnit4.class)
public class BasicUserInteractionTest {

    private static final int CHEESECAKE_RECIPE = 3;

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void scrollToLastRecipe_checkItsName() {
        // Checking if all the recipes are being displayed.
        String recipe1, recipe2, recipe3, recipe4;
        recipe1 = "Nutella Pie";
        recipe2 = "Brownies";
        recipe3 = "Yellow Cake";
        recipe4 = "Cheesecake";

        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText(recipe1)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(2));
        onView(withText(recipe2)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText(recipe3)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(4));
        onView(withText(recipe4)).check(matches(isDisplayed()));
    }

    @Test
    public void scrollToLastRecipe_displaysFragmentStepslist() {
        //Scrolling to the last recipe
        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(4));

        //Clicking at the last recipe
        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(CHEESECAKE_RECIPE, click()));

        //This checks if the fragments that displays the ingredients and steps is being displayed
        onView(ViewMatchers.withId(R.id.ingredients_title_tv)).check(matches(isDisplayed()));
    }

    //@Test Not working well with NestedScrollViews, need further study to know why
    public void scrollToLastRecipe_scrollToLastStep_displaysFragmentDetails() {
        //Scrolling to the last recipe
        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(4));

        //Clicking at the last recipe
        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(CHEESECAKE_RECIPE, click()));

        //Due to a few issues I found with espresso on NestedScrollViews, I'll swipe up or down as needed
        //Any tips on how to improve the usage of Espresso with NestedScrollViews and Coordinatorlayouts is greatly appreciated
        onView(ViewMatchers.withId(R.id.step_details_nsv)).perform(ViewActions.swipeUp());

        //Finds the step and clicks on it
        onView(ViewMatchers.withId(R.id.step_list))
                .perform(RecyclerViewActions.scrollToPosition(4));

        onView(ViewMatchers.withId(R.id.step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        //Checks if the step details are being displayed
        onView(ViewMatchers.withId(R.id.step_description)).check(matches(isDisplayed()));
    }
}
