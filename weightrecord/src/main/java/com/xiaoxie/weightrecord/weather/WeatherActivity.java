package com.xiaoxie.weightrecord.weather;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;

import com.xcb.network.okhttp.callback.StringCallback;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.weather.utils.GetWeatherUtils;
import com.xiaoxie.weightrecord.weather.view.AQIView;
import com.xiaoxie.weightrecord.weather.view.ComfortView;
import com.xiaoxie.weightrecord.weather.view.SunRiseDownView;
import com.xiaoxie.weightrecord.weather.view.SunRiseView;
import com.xiaoxie.weightrecord.weather.view.TemperatureView;
import com.xiaoxie.weightrecord.weather.view.WeatherForecast;
import com.xiaoxie.weightrecord.weather.view.WindView;

import okhttp3.Call;

/**
 * Created by xcb on 2018/1/12.
 */

public class WeatherActivity extends Activity implements View.OnClickListener {
    private static final String[] PERMISSION_ACCESS_FINE_LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String[] PERMISSION_ACCESS_COARSE_LOCATION = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int F_REQUESTCODE = 1000;
    private static final int C_REQUESTCODE = 1001;
    private LinearLayout footView;
    private ScrollView mScrollView;
    private LinearLayout scrollView_container;
    private TemperatureView temperatureView;
    private AQIView aqiView;
    private ComfortView comfortView;
    private WindView windView;
    private SunRiseDownView sunRiseDownView;
    private WeatherForecast weatherForecast;

    private ImageView iv_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setContentView(R.layout.activity_weather);
        checkPermission();
        initView();

       /* GetWeatherUtils.get6DaysInfo(null, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, String id) {
                Log.d("weather", e.toString());
            }

            @Override
            public void onResponse(String response, String id) {
                Log.d("weather", response);
            }
        });*/
    }

    private void initView() {
        iv_setting = findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);

        footView = findViewById(R.id.weather_foot);
        mScrollView = findViewById(R.id.scrollView);
        scrollView_container = findViewById(R.id.scrollView_container);
        footView.setBackgroundResource(R.drawable.ic_weather_bg_sunny);
        temperatureView = new TemperatureView(this);
        aqiView = new AQIView(this);
        comfortView = new ComfortView(this);
        windView = new WindView(this);
        sunRiseDownView = new SunRiseDownView(this);
        weatherForecast = new WeatherForecast(this);
        scrollView_container.addView(temperatureView);
        scrollView_container.addView(weatherForecast);
        addDividingLine(scrollView_container);
        scrollView_container.addView(aqiView);
        addDividingLine(scrollView_container);
        scrollView_container.addView(comfortView);
        addDividingLine(scrollView_container);
        scrollView_container.addView(windView);
        addDividingLine(scrollView_container);
        scrollView_container.addView(sunRiseDownView);
        addDividingLine(scrollView_container);
    }

    /**
     * 添加分割线
     */
    private void addDividingLine(LinearLayout container) {
        View dividingLine = new View(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getWidth() - 30, 1);
        layoutParams.setMargins(15, 15, 15, 15);
        dividingLine.setLayoutParams(layoutParams);
        dividingLine.setBackgroundColor(getResources().getColor(R.color.color_9b9c9b, null));
        container.addView(dividingLine);
    }

    /**
     * 获取屏幕宽度
     */
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting:
                Intent intent = new Intent();
                intent.setClass(this, WeatherSettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out);
                break;
        }
    }
}
