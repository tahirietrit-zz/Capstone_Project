package widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.tahirietrit.ireport.R;

/**
 * Created by macb on 13/05/16.
 */
public class MyWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // initializing widget layout
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        remoteViews.setTextViewText(R.id.report_title, "Latest Reports on codeing");
        remoteViews.setTextViewText(R.id.report_author, "Etrit Tahiri");

        // request for widget update
        pushWidgetUpdate(context, remoteViews);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context,
                MyWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}