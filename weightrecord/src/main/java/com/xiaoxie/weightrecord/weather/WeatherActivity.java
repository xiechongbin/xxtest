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
import android.util.Log;
import android.widget.LinearLayout;

import com.xcb.network.okhttp.callback.StringCallback;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.weather.utils.GetWeatherUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().
        setContentView(R.layout.activity_weather);
        footView = findViewById(R.id.weather_foot);
        footView.setBackgroundResource(R.drawable.ic_weather_bg_sunny);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSION_ACCESS_COARSE_LOCATION, C_REQUESTCODE);
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_ACCESS_FINE_LOCATION, F_REQUESTCODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        GetWeatherUtils.get6DaysInfo(location, new StringCallback() {
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
}
