package com.xiaoxie.weightrecord.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.bean.BodyData;
import com.xiaoxie.weightrecord.interfaces.ActionBarClickListener;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener1;
import com.xiaoxie.weightrecord.realm.RealmStorageHelper;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.ActionbarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.RealmResults;

public class TrendDetailsActivity extends AppCompatActivity implements ActionBarClickListener, View.OnClickListener {
    private ActionbarView view;
    private ImageView igv_interval;//区间选择
    private TextView tv_interval_title;
    private TextView tv_trend_details_month_range;
    private Calendar calendar = Calendar.getInstance();
    private List<BodyData> bodyDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_details);
        setCustomActionBar();
        initView();
    }

    private void initView() {
        igv_interval = (ImageView) findViewById(R.id.iv_trend_details_month);
        tv_interval_title = (TextView) findViewById(R.id.tv_interval_title);
        tv_trend_details_month_range = (TextView) findViewById(R.id.tv_trend_details_month_range);
        igv_interval.setOnClickListener(this);
    }

    /**
     * 自定义标题栏
     */
    private void setCustomActionBar() {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        view = new ActionbarView(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setCustomView(view, layoutParams);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        view.setOnActionBarClickListener(this);
        view.setWitchToShow(TrendDetailsActivity.class.getSimpleName());
    }

    @Override
    public void onActionBarClick(int id) {
        if (id == R.id.img_menu) {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_trend_details_month:
                showIntervalDialog();
                break;
        }
    }

    /**
     * 区间选择对话框
     */
    private void showIntervalDialog() {
        CustomDialog.IntervalBuilder builder = new CustomDialog.IntervalBuilder(this);
        builder.setOnDialogClickListener(new DialogClickListener1() {
            @Override
            public void OnConfirmed(Object obj) {
                int id = (int) obj;
                switch (id) {
                    case R.id.tv_interval_week:
                        tv_interval_title.setText(R.string.label_week);
                        break;
                    case R.id.tv_interval_month:
                        tv_interval_title.setText(R.string.label_month);
                        String str = setInterverValue();
                        tv_trend_details_month_range.setText(str);
                        break;
                    case R.id.tv_interval_year:
                        tv_interval_title.setText(R.string.label_year);
                        break;
                    case R.id.tv_interval_custom:
                        tv_interval_title.setText(R.string.label_custom);
                        break;
                }
            }

            @Override
            public void OnCanceled() {
            }
        });
        int a = getSupportActionBar().getHeight();
        Log.d("ccdd", "actionbar a = " + a);
        Dialog dialog = Utils.setCostumeDialogStyle(builder.create(), this, 0.5f, 0.3f, Utils.dip2px(this, 15), a + Utils.dip2px(this, 65), Gravity.LEFT, Gravity.TOP);
        Window window = dialog.getWindow();
        if (window != null)
            window.setWindowAnimations(R.style.interval_animation_style);
        dialog.show();
    }

    /**
     * 设置区间的值
     */
    private String setInterverValue() {
        long firstOfMonth = 0;
        long lastOfMonth = 0;
        int lastday;
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            firstOfMonth = year * 10000 + month * month * 100 + 1;
            lastOfMonth = year * 10000 + month * month * 100 + 31;
            lastday = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            firstOfMonth = year * 10000 + month * month * 100 + 1;
            lastOfMonth = year * 10000 + month * month * 100 + 30;
            lastday = 30;
        } else {
            firstOfMonth = year * 10000 + month * month * 100 + 1;
            if (year % 4 == 0) {
                lastOfMonth = year * 10000 + month * month * 100 + 28;
                lastday = 28;
            } else {
                lastOfMonth = year * 10000 + month * month * 100 + 29;
                lastday = 29;
            }

        }
        return month + " 月 1 --" + month + " 月 " + lastday;
    }

    private void queryData(long from, long to) {
        RealmResults<BodyData> results = RealmStorageHelper.getInstance()
                .getRealm()
                .where(BodyData.class)
                .greaterThanOrEqualTo("timeStamp", from)
                .or()
                .lessThanOrEqualTo("timeStamp", to)
                .findAll();
        int size = results.size();
        if (bodyDataList == null) {
            bodyDataList = new ArrayList<>();
        }
        bodyDataList.clear();
        for (int i = 0; i < size; i++) {
            BodyData data = results.get(i);
            Log.d("results1", "date = " + data.getDate() + ">>>AverageWeight = " + data.getAverageWeight()
                    + ">>>Amweight = " + data.getAmWeight()
                    + ">>>pmweight = " + data.getPmWeight()
                    + ">>>Acvitvity = " + data.getActivity()
                    + ">>>chest = " + data.getChest()
                    + ">>>timtstamp = " + data.getTimeStamp());
            bodyDataList.add(data);
        }
    }
}
