package br.com.udacity.bakingtime.utilities;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class IngredientsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new IngredientListProvider(this.getApplicationContext(), intent));
    }
}
