package com.tahirietrit.ireport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragments.FeedFragment;
import fragments.ProfileFragment;

public class MainActivity extends FragmentActivity {
    @Bind(R.id.feed_button)
    Button feedButton;
    @Bind(R.id.profile_button)
    Button profileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new FeedFragment())
                    .commit();
        }
        if (BuildConfig.DEBUG) {
            System.out.println("Mode DEBUG");
        }else{
            System.out.println("Mode RELEASE");
        }
    }
    @OnClick(R.id.profile_button)
    void openProfileFragment(){

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .commit();
    }
    @OnClick(R.id.feed_button)
    void openFeedFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FeedFragment())
                .commit();
    }


    @OnClick(R.id.create_button)
    void openCreateActivity(){
        startActivity(new Intent(getApplicationContext(), CreateReportActivity.class));


    }
}
