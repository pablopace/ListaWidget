package com.pablo.listawidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;


// lo unico que debe hacer es darme un RemoteViewsFactory.
public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // no se para que quiero el id.
        // en la documentacion no dice para que
        //int appWidgetId  = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListProvider(this.getApplicationContext(), intent);
    }
}

// este es el ADAPTER del ListView
class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    /**
     * Como es un ADAPTER de datos deberia tener una
     * lista de items que va a entregar.
     * <p/>
     * Le va a dar al appwidget los layout de los items de la coleccion
     * a medida que el widget los solicite.
     * <p/>
     * Hay que implementear onCreate() y getViewAt()
     * y luego todos los metodos de esta interface.
     */

    private Context context;
    private ArrayList<Persona> personas = new ArrayList<Persona>();
    private int appWidgetId;

    public ListProvider(Context c, Intent intent) {
        context = c;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        for (int i = 0; i < 10; i++) {
            personas.add(new Persona("Pablo" + i, i * 2 + 10));
        }

        // We sleep for 3 seconds here to show how the empty view appears in the interim.
        // The empty view is set in the StackWidgetProvider and should be a sibling of the
        // collection view.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        personas.clear();
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.item);
        rv.setTextViewText(R.id.nombre, personas.get(position).nombre);
        rv.setTextViewText(R.id.edad, personas.get(position).getEdad());

        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.
        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    // The number of types of Views that will be returned by this factory.
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}


