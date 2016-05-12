package com.tahirietrit.ireport;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import customcomponents.CameraClass;
import customcomponents.TextureViewPortrait;
import okhttp3.MediaType;
import okhttp3.RequestBody;
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
public class CreateReportActivity extends Activity {
    @Bind(R.id.surfaceView)
    TextureViewPortrait surfaceView;
    @Bind(R.id.capture_holder)
    RelativeLayout captureHolder;
    @Bind(R.id.compose_holder)
    RelativeLayout composeHolder;
    @Bind(R.id.captured_image)
    ImageView capturedImage;
    @Bind(R.id.report_text_compose)
    EditText reportText;

    CameraClass cameraClass;
    File reportPicture;
    RequestCallBack reqCall;
    Retrofit retrofit;
    public static final int MULTIPLE_PERMISSIONS = 1;
    private Tracker mTracker;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_report_layout);
        ButterKnife.bind(this);

        if(doesHaveCameraPermission() && doesHaveReadStoragePermission() && doesHaveWriteStoragePermission()) {
            cameraClass = new CameraClass(surfaceView, this);
        }else{
            System.out.println("else");
            ActivityCompat.requestPermissions(CreateReportActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    MULTIPLE_PERMISSIONS);
        }
        IReport application = (IReport) getApplication();
        mTracker = application.getDefaultTracker();

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        try {
            cameraClass.onResume();
        }catch (Exception e){

        }
        mTracker.setScreenName("Create Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            cameraClass.onPause();
        }catch (Exception e){

        }
    }

    @OnClick(R.id.button_capture)
    void takePicture() {
        Bitmap bitmap = cameraClass.getBitmapFromCameraCapture();
        System.out.println("bitmap " + bitmap);
        captureHolder.setVisibility(View.GONE);
        composeHolder.setVisibility(View.VISIBLE);
        capturedImage.setImageBitmap(bitmap);
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/iReport";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        reportPicture = new File(dir, "report.png");
        try {
            FileOutputStream fOut = new FileOutputStream(reportPicture);

            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @OnClick(R.id.publish_button)
    void uploadReport() {
        if (reportText.getText().toString().length() > 0) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utilitys.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            reqCall = retrofit.create(RequestCallBack.class);


            RequestBody fbody = RequestBody.create(MediaType.parse(""), reportPicture);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), reportText.getText().toString());
            RequestBody title = RequestBody.create(MediaType.parse("text/plain"), "title");
            Call<String> uploadCall = reqCall.uploadReport(title, name, fbody, AppPreferences.getAccessToken());
            uploadCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                }

                @Override
                public void onFailure(Call<String> cal, Throwable t) {
                    reportPicture.delete();
                    finish();
                }
            });
            ProgressDialog progress = ProgressDialog.show(this, "iReport",
                    "Uploading report", true);
        } else {
            Toast.makeText(getApplicationContext(), "Please write your report than try again", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean doesHaveWriteStoragePermission() {
        int result = getApplicationContext().checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private boolean doesHaveCameraPermission(){
        int result = getApplicationContext().checkCallingOrSelfPermission(Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private boolean doesHaveReadStoragePermission(){
        int result = getApplicationContext().checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraClass = new CameraClass(surfaceView, this);
                    onResume();
                } else {
                    finish();
                }
                return;
            }

        }
    }
}
