package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;
import com.tahirietrit.ireport.IReport;
import com.tahirietrit.ireport.R;

import adapters.ProfileAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import customcomponents.CircleTransform;
import objects.UserProfile;
import requests.RequestCallBack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.AppPreferences;
import utils.Utilitys;

/**
 * Created by macb on 11/05/16.
 */
public class ProfileFragment extends Fragment {
    @Bind(R.id.profile_recyclerview)
    RecyclerView profileRecyclerview;
    RequestCallBack reqCall;
    Retrofit retrofit;
    private RecyclerView.LayoutManager mLayoutManager;
    ProfileAdapter profileAdapter;
    @Bind(R.id.profile_image)
    ImageView profileImage;
    @Bind(R.id.username_textview)
    TextView usernameTextView;
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout ,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        profileRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        profileRecyclerview.setLayoutManager(mLayoutManager);

        profileAdapter = new ProfileAdapter(getActivity());
        profileRecyclerview.setAdapter(profileAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);
        Call<UserProfile> profileCall = reqCall.getUserProfile("1", AppPreferences.getAccessToken());
        profileCall.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(response.code() == 200) {
                    Picasso.with(getActivity()).load(response.body().getProfilePic()).transform(new CircleTransform()).into(profileImage);
                    usernameTextView.setText(response.body().getName());
                    profileAdapter.setArticles(response.body().getArticles(), response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
        IReport application = (IReport) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Feed Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
