package com.example.vslogin;

import android.content.Intent;
import android.content.IntentFilter;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class SuccessActivity extends AppCompatActivity {
    private static final String TAG = "VSLogin";
    private static final String CONTAINER = "data_container";

    private DataContainer container;
    private TextView batteryLbl;
    private TextView sdkLbl;
    private TextView releaseLbl;
    private TextView deviceLbl;
    private TextView ramLbl;
    private TextView volumeLbl;
    private TextView orientationLbl;
    private TextView appInstalled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Gson gson = new Gson();
        container = gson.fromJson(getIntent().getStringExtra(CONTAINER), DataContainer.class);
        Log.d(TAG, "onCreate: Got container: " + container.toString());
        initViews();
    }

    private void initViews() {
        batteryLbl = findViewById(R.id.success_LBL_batteryLevel);
        batteryLbl.setText("Battery: " + container.getBatteryLevel() + "%");
        sdkLbl = findViewById(R.id.success_LBL_sdkVersion);
        sdkLbl.setText("SDK: " + container.getSdkVersion());
        releaseLbl = findViewById(R.id.success_LBL_releaseVersion);
        releaseLbl.setText("Release: " + container.getReleaseVersion());
        deviceLbl = findViewById(R.id.success_LBL_deviceName);
        deviceLbl.setText("Device: " + container.getDeviceName());
        ramLbl = findViewById(R.id.success_LBL_deviceRam);
        ramLbl.setText("Ram: " + container.getDeviceRam() + "GB");
        volumeLbl = findViewById(R.id.success_LBL_deviceVolume);
        volumeLbl.setText("Volume: " + container.getDeviceVolume() + "%");
        orientationLbl = findViewById(R.id.success_LBL_deviceOrientation);
        orientationLbl.setText("Orientation: " + container.getDeviceOrientation());
        appInstalled = findViewById(R.id.success_LBL_appInstalled);
        appInstalled.setText(container.getAppInstalled() + " package installed!");
    }
}
