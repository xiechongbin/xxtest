package com.xiaoxie.weightrecord.weather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.xcb.network.okhttp.callback.StringCallback;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.weather.utils.GetWeatherUtils;
import com.xiaoxie.weightrecord.weather.view.AQIView;
import com.xiaoxie.weightrecord.weather.view.CircleView;
import com.xiaoxie.weightrecord.weather.view.TemperatureView;

import okhttp3.Call;

/**
 * Created by xcb on 2018/1/12.
 */

public class WeatherActivity extends Activity {
    private static final String[] PERMISSION_ACCESS_FINE_LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String[] PERMISSION_ACCESS_COARSE_LOCATION = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int F_REQUESTCODE = 1000;
    private static final int C_REQUESTCODE = 1001;
    private LinearLayout footView;
    private ScrollView mScrollView;
    private LinearLayout scrollView_container;
    private TemperatureView temperatureView;
    private AQIView aqiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setContentView(R.layout.activity_weather);
        checkPermission();
        initView();

        GetWeatherUtils.get6DaysInfo(null, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, String id) {
                Log.d("weather", e.toString());
            }

            @Override
            public void onResponse(String response, String id) {
                Log.d("weather", response);
            }
        });
    }

    private void initView() {
        footView = findViewById(R.id.weather_foot);
        mScrollView = findViewById(R.id.scrollView);
        scrollView_container = findViewById(R.id.scrollView_container);
        footView.setBackgroundResource(R.drawable.ic_weather_bg_sunny);
        temperatureView = new TemperatureView(this);
        aqiView = new AQIView(this);
        scrollView_container.addView(temperatureView);
        scrollView_container.addView(aqiView);
    }

    private int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSION_ACCESS_COARSE_LOCATION, C_REQUESTCODE);
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_ACCESS_FINE_LOCATION, F_REQUESTCODE);
        }
    }
}
