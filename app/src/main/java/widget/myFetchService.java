package widget;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by macb on 13/05/16.
 */
public class myFetchService extends IntentService {

    public myFetchService() {
        super("myFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getExtras() != null) {
            Intent service_start = new Intent(myFetchService.this, MyWidgetIntentReceiver.class);
            service_start.putExtra("title", intent.getExtras().getString("title"));
            service_start.putExtra("author", intent.getExtras().getString("author"));
            sendBroadcast(service_start);
        }
    }
}