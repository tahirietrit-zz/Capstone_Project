package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tahirietrit.ireport.IReport;
import com.tahirietrit.ireport.R;

import adapters.FeedAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import objects.MainFeed;
import requests.RequestCallBack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.AppPreferences;
import utils.Utilitys;

/**
 * Created by macb on 12/04/16.
 */
public class FeedFragment extends Fragment {
    @Bind(R.id.feed_recyclerview)
    RecyclerView feedRecyclerview;

    private RecyclerView.LayoutManager mLayoutManager;
    FeedAdapter feedAdapter;

    RequestCallBack reqCall;
    Retrofit retrofit;
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_fragment_layout ,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        feedRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerview.setLayoutManager(mLayoutManager);

        feedAdapter = new FeedAdapter(getActivity());
        feedRecyclerview.setAdapter(feedAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);
        IReport application = (IReport) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Override
    public void onResume() {
        super.onResume();
        Call<MainFeed> mainFeedCall = reqCall.mainFeed(AppPreferences.getAccessToken());
        mainFeedCall.enqueue(new Callback<MainFeed>() {
            @Override
            public void onResponse(Call<MainFeed> call, Response<MainFeed> response) {
                feedAdapter.setArticles(response.body().getItems());
            }

            @Override
            public void onFailure(Call<MainFeed> call, Throwable t) {

            }
        });
        mTracker.setScreenName("Feed Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    
}
