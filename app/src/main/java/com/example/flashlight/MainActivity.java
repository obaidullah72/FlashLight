package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;

    boolean hasCamFlash = false;
    boolean FlashOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = findViewById(R.id.img_btn);

        hasCamFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasCamFlash){
                    if(FlashOn){
                        FlashOn =false;
                        toggleButton.setImageResource(R.drawable.poweroff);
                        try {
                            flashlightoff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }else{
                        FlashOn = true;
                        toggleButton.setImageResource(R.drawable.poweron);
                        try {
                            flashlighton();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "No Flash Available", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void flashlighton() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
        Toast.makeText(MainActivity.this, "FlashLight is On", Toast.LENGTH_SHORT ).show();
    }
    private void flashlightoff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, false);
        Toast.makeText(MainActivity.this, "FlashLight is OFF", Toast.LENGTH_SHORT).show();
    }
}