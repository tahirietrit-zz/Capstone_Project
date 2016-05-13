package widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.tahirietrit.ireport.R;

/**
 * Created by macb on 13/05/16.
 */
public class MyWidgetIntentReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        updateWidgetPictureAndButtonListener(context, intent.getExtras().getString("title"), intent.getExtras().getString("author"));
    }

    private void updateWidgetPictureAndButtonListener(Context context, String title, String author) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        remoteViews.setTextViewText(R.id.report_title, title);
        remoteViews.setTextViewText(R.id.report_author, author);

        MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }
}