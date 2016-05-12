package customcomponents;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.view.TextureView;

import java.io.IOException;
import java.util.List;

public class CameraClass {

    private static final String TAG = "CameraClass";

    private TextureViewPortrait mTextureView;

    private Activity activity;

    private int mCameraID;

    public Camera mCamera;

    public CameraClass(TextureViewPortrait _mTextureView, Activity _activity) {

        this.mTextureView = _mTextureView;
        this.activity = _activity;
    }

    private final TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            openCamera(mCameraID);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            onPause();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };

    private void openCamera(final int cameraId) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mCamera = Camera.open(cameraId);
                    mCamera.setDisplayOrientation(90);
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setCameraParameters();
            }

        }.execute();

    }

    private void setCameraParameters() {

        try {
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size bestPreviewSize = determineBestPreviewSize(parameters);
            parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);

            if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
            mCamera.setParameters(parameters);
            try {
                mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
            } catch (IOException e) {
            }
            mCamera.startPreview();
        } catch (Exception ex) {
        }
    }

    public void onResume() {
        if (mTextureView.isAvailable()) {
            openCamera(mCameraID);
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    public void onPause() {
        closeCamera();
    }

    public void switchCamera() {
        try {
            if (mCameraID == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mCameraID = getBackCameraID();
            } else {
                mCameraID = getFrontCameraID();
            }
            closeCamera();
            onResume();
        } catch (Exception ex) {
        }
    }

    private void closeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    private Camera.Size determineBestPreviewSize(Camera.Parameters parameters) {

        return determineBestSize(parameters.getSupportedPreviewSizes());

    }

    private Camera.Size determineBestSize(List<Camera.Size> sizes) {
        Camera.Size bestSize = null;
        Camera.Size size;
        int numOfSizes = sizes.size();
        for (int i = 0; i < numOfSizes; i++) {
            size = sizes.get(i);
            boolean isDesireRatio = (size.width / 4) == (size.height / 3);
            boolean isBetterSize = (bestSize == null) || size.width > bestSize.width;

            if (isDesireRatio && isBetterSize) {
                bestSize = size;
            }
        }

        if (bestSize == null) {
            return sizes.get(sizes.size() - 1);
        }

        return bestSize;
    }

    private int getFrontCameraID() {
        PackageManager pm = activity.getApplicationContext().getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            return Camera.CameraInfo.CAMERA_FACING_FRONT;
        }

        return getBackCameraID();
    }

    private int getBackCameraID() {
        return Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    public void takePictureFromCamera() {
        try {
            mCamera.takePicture(shutterCallback, rawCallback, pictureCallback);
        } catch (Exception ex) {
        }
    }


    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
        }
    };
    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] _data, Camera _camera) {
        }
    };
    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            mTextureView.post(new Runnable() {
                @Override
                public void run() {

                }
            });


        }
    };


    public Bitmap getBitmapFromCameraCapture() {

        Bitmap bitmapFromSurface = mTextureView.getBitmap();




        return bitmapFromSurface;
    }
}