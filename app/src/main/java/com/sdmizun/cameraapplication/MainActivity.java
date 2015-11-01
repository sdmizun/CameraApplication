package com.sdmizun.cameraapplication;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private ImageButton mButton;
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private FrameLayout mFrameLayout;
    private boolean isPreview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = (FrameLayout) findViewById(R.id.camera_preview);
        mButton = (ImageButton) findViewById(R.id.camera_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPreview) {
                    captureCamera();
                    isPreview = true;
                } else {
                    releaseCamera();
                    isPreview = false;
                }
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (isPreview){
            captureCamera();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        releaseCamera();
    }

    private void captureCamera(){
        mCamera = getCameraInstance();
        if (mCamera == null)
            finish();
        mCameraPreview = new CameraPreview(getBaseContext(), mCamera);
        mFrameLayout.addView(mCameraPreview);
        mFrameLayout.setVisibility(View.VISIBLE);
    }
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
        if (mCameraPreview != null) {
            mCameraPreview.getHolder().removeCallback(mCameraPreview);
            mCameraPreview = null;
        }
        if (mFrameLayout != null){
            mFrameLayout.removeAllViews();
            mFrameLayout.setVisibility(View.INVISIBLE);
        }
    }

    private Camera getCameraInstance(){
        Camera c = null;
        try{
            c = Camera.open();
        } catch (Exception e){

        }
        return c;
    }

}
