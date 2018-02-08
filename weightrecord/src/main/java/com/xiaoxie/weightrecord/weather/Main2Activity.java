package com.xiaoxie.weightrecord.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.weather.view.BoardView;
import com.xiaoxie.weightrecord.weather.view.CircleView;

public class Main2Activity extends AppCompatActivity {
    private BoardView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        boardView = (BoardView) findViewById(R.id.boardView);
       // boardView.startAnimation();
    }
}
