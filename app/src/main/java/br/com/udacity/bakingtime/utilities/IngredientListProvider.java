package br.com.udacity.bakingtime.utilities;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import br.com.udacity.bakingtime.R;

class IngredientListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<String> mItems = new ArrayList<>();
    private Context mContext = null;

    IngredientListProvider(Context applicationContext, Intent intent) {
        this.mContext = applicationContext;
        this.mItems = intent.getStringArrayListExtra(
                applicationContext.getString(R.string.service_recipe_ingredients));
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        mItems.clear();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        String data = mItems.get(i);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.recipe_ingredient_list_item);
        rv.setTextViewText(R.id.ingredient_item, data);
        rv.setEmptyView(R.id.appwidget_ingredient_list, R.id.empty_view);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        //Only displaying TextViews
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
