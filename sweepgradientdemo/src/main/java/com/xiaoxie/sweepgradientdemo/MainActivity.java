package com.xiaoxie.sweepgradientdemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScanView scanView = new ScanView(this);
        //随机获得一组坐标

        scanView.setOnPointUpdateListener(new ScanView.OnPointUpdateListener() {
            @Override
            public void OnUpdate(Canvas canvas, Paint paintPoint, float cx, float cy,
                                 float radius) {
            }
        });
        scanView.start();
        setContentView(scanView);
    }
}
