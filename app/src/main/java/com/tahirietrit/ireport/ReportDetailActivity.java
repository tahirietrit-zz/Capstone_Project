package com.tahirietrit.ireport;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import customcomponents.CircleTransform;
import objects.Report;
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
public class ReportDetailActivity extends Activity {
    @Bind(R.id.report_image)
    ImageView reportImage;
    @Bind(R.id.author_profile_pic)
    ImageView reporterImage;
    @Bind(R.id.author_name)
    TextView authorName;
    @Bind(R.id.report_content)
    TextView reportContent;
    int reportId;

   RequestCallBack reqCall;
    Retrofit retrofit;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail_layout);
        ButterKnife.bind(this);
        reportId = getIntent().getIntExtra("reportId",1);
        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);
        Call<Report> reportCall = reqCall.getReportById(String.valueOf(reportId), AppPreferences.getAccessToken());
        reportCall.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                Picasso.with(getApplicationContext()).load(response.body().getImage()).into(reportImage);
                Picasso.with(getApplicationContext()).load(response.body().getCreator().getProfilePic())
                        .transform(new CircleTransform()).into(reporterImage);
                authorName.setText(response.body().getCreator().getName());
                reportContent.setText(response.body().getDescription());
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {

            }
        });
        IReport application = (IReport) getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Detail Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
