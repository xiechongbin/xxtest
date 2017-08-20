package com.xiaoxie.weightrecord.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.view.CalendarView;

public class MainActivity extends Activity {
    private CalendarView calendarView;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

    }


}
