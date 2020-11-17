package com.example.vslogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "VSLogin";
    private static final String APP_PACKAGE = "com.twitter.android";
    private static final String OPEN_KEY = "רק בחיפה יש מכבי";
    private static final String SAMSUNG_S20_PLUS = "Samsung SM-G985F";
    private static final String ANDROID_10_SDK_29 = "Android SDK: 29 (10)";
    private static final String PORTRAIT = "Portrait";
    private static final String CONTAINER = "data_container";
    private static final String LANDSCAPE = "Landscape";


    private EditText inputText;
    private MaterialButton submitBtn;


    private DataContainer container;
    private int batteryLevel = 0;
    private String androidVersion = "";
    private String deviceName = "";
    private String totalRam = "";
    private String deviceVolume = "";
    private String deviceOrientation = "";

    private long intDeviceRam = 0;
    private int intDeviceVolume = 0;


    private boolean appInstalled = false;

    /**
     * Get device battery
     */
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            container.setBatteryLevel(batteryLevel);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkValidity();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = new DataContainer();
        inputText = findViewById(R.id.main_EDT_inputBox);
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                submitBtn.setBackgroundColor(getColor(R.color.colorPrimary));
                submitBtn.setText("Try me");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        submitBtn = findViewById(R.id.main_BTN_submitBtn);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    /**
     * A method to check the phone stats and user input
     */
    private void checkValidity() {
        Log.d(TAG, "checkValidity: ");
        getAndroidVersion();
        getDeviceRam();
        getCurrentVolume();
        checkPhoneOrientation();
        if (checkIfAppInstalled(APP_PACKAGE)) {
            appInstalled = true;
        } else {
            appInstalled = false;
        }
        deviceName = getDeviceName();

        if (inputText.getText().toString().equals(OPEN_KEY)
                && deviceName.equals(SAMSUNG_S20_PLUS)
                && batteryLevel > 50
                && androidVersion.equals(ANDROID_10_SDK_29)
                && intDeviceRam > 5
                && intDeviceVolume > 50
                && deviceOrientation.equals(PORTRAIT)
                && appInstalled) {
            Gson gson = new Gson();
            Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
            intent.putExtra(CONTAINER, gson.toJson(container));
            startActivity(intent);
            finish();
        } else {
            submitBtn.setBackgroundColor(getColor(R.color.colorSecondary));
            submitBtn.setText("Try again");
        }
    }

    /**
     * A method to check if a specific package is installed
     */
    private boolean checkIfAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            container.setAppInstalled(APP_PACKAGE);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    /**
     * A method to check phone orientation
     */
    private void checkPhoneOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            deviceOrientation = LANDSCAPE;
            container.setDeviceOrientation(LANDSCAPE);
        } else {
            deviceOrientation = PORTRAIT;
            container.setDeviceOrientation(PORTRAIT);
        }
        Log.d(TAG, "checkLandscapePortrait: " + deviceOrientation);
    }

    /**
     * Get android release & sdk
     */
    public void getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        int releaseInt = Integer.valueOf(release);

        container.setSdkVersion(sdkVersion);
        container.setReleaseVersion(releaseInt);

        androidVersion = "Android SDK: " + sdkVersion + " (" + release + ")";
        //Release: 10 | sdkVersion: 29
        Log.d(TAG, "getAndroidVersion: " + androidVersion);
    }

    /**
     * Get device name
     */
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            container.setDeviceName(capitalize(model));
            return capitalize(model);
        } else {
            container.setDeviceName(capitalize(manufacturer) + " " + model);
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * Get device RAM
     */
    private void getDeviceRam() {
        ActivityManager actManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        intDeviceRam = memInfo.totalMem / (1024 * 1024 * 1024);
        container.setDeviceRam(intDeviceRam);
        totalRam = "" + (memInfo.totalMem / (1024 * 1024 * 1024)) + " GB";
        Log.d(TAG, "GetDeviceRam: " + totalRam);
    }

    /**
     * A method to get current volume
     */
    private void getCurrentVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        deviceVolume = "" + (100 * currentVolume / maxVolume);
        intDeviceVolume = (100 * currentVolume / maxVolume);
        container.setDeviceVolume(intDeviceVolume);
        Log.d(TAG, "getCurrentVolume: " + deviceVolume + "%");
    }


}