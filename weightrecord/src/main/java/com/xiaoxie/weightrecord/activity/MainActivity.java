package com.xiaoxie.weightrecord.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.Utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.view.CalendarView;
import com.xiaoxie.weightrecord.view.DashBoardView;
import com.xiaoxie.weightrecord.view.StepArcView;

public class MainActivity extends Activity {
    private CalendarView calendarView;
    private GridLayout gridLayout;
    private StepArcView stepArcView;
    private DashBoardView dashBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_IS_FIRST_LOAD, true)) {
            Intent intent = new Intent();
            intent.setClass(this, introActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
    }


}
