package fragments;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import objects.Item;
import objects.MainFeed;

/**
 * Created by macb on 13/05/16.
 */
public class FeedLoader extends AsyncTaskLoader<List<Item>> {
    MainFeed mainFeed;
    public FeedLoader (Context context, MainFeed mainFeed) {
        super(context);
        this.mainFeed = mainFeed;
    }

    @Override
    public List<Item> loadInBackground() {

        return mainFeed.getItems();
    }
}