package com.xiaoxie.weightrecord.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.activity.MainActivity;
import com.xiaoxie.weightrecord.activity.TrendDetailsActivity;
import com.xiaoxie.weightrecord.bean.BodyData;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener1;
import com.xiaoxie.weightrecord.realm.RealmStorageHelper;
import com.xiaoxie.weightrecord.utils.CalculationUtils;
import com.xiaoxie.weightrecord.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by xcb on 2017/10/19.
 * 趋势分析View
 */

public class TrendAnalysisView extends LinearLayout implements View.OnClickListener {
    private View trendAnalysisView;
    private Context context;

    private LinearLayout ll_type_choice;
    private LinearLayout ll_open_details;
    private LinearLayout ll_future;

    private ImageView iv_last_seven_days;
    private TextView tv_last_seven_days;

    private ImageView iv_this_month_to_today;
    private TextView tv_this_month_to_today;

    private ImageView iv_this_year_to_today;
    private TextView tv_this_year_to_today;

    private ImageView iv_everyday;
    private TextView tv_everyday;

    private ImageView iv_every_week;
    private TextView tv_every_week;

    private ImageView iv_every_month;
    private TextView tv_every_month;

    private TextView tv_future_seven_days_value;
    private TextView tv_future_seven_days_date;

    private TextView tv_future_end_of_month_value;
    private TextView tv_future_end_of_month_date;

    private TextView tv_future_end_of_year_value;
    private TextView tv_future_end_of_year_date;

    private List<BodyData> bodyDataList;

    private BodyData todayBodyData = null;
    private BodyData sevenDayAgoBodyData = null;
    private BodyData monthAgoBodyData = null;
    private BodyData yearAgoBodyData = null;

    private long todayTimestamp;
    private long sevenDaysAgoTimestamp;
    private long monthAgoTimestamp;
    private long yearAgoTimestamp;
    private Calendar calendar = Calendar.getInstance();

    public TrendAnalysisView(Context context) {
        super(context);
        initView(context);
    }

    public TrendAnalysisView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TrendAnalysisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        trendAnalysisView = LayoutInflater.from(context).inflate(R.layout.layout_trend_analysis, null);
        trendAnalysisView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(trendAnalysisView);
        findViews();
        initData();
    }

    private void findViews() {
        ll_type_choice = trendAnalysisView.findViewById(R.id.ll_type_choice);
        ll_open_details = trendAnalysisView.findViewById(R.id.ll_open_details);
        ll_future = trendAnalysisView.findViewById(R.id.ll_future);

        iv_last_seven_days = trendAnalysisView.findViewById(R.id.iv_last_seven_days);
        tv_last_seven_days = trendAnalysisView.findViewById(R.id.tv_last_seven_days_value);

        iv_this_month_to_today = trendAnalysisView.findViewById(R.id.iv_this_month_to_today);
        tv_this_month_to_today = trendAnalysisView.findViewById(R.id.tv_this_month_to_today_value);

        iv_this_year_to_today = trendAnalysisView.findViewById(R.id.iv_this_year_to_today);
        tv_this_year_to_today = trendAnalysisView.findViewById(R.id.tv_this_year_to_today_value);

        iv_everyday = trendAnalysisView.findViewById(R.id.iv_everyday);
        tv_everyday = trendAnalysisView.findViewById(R.id.tv_everyday_value);

        iv_every_week = trendAnalysisView.findViewById(R.id.iv_every_week);
        tv_every_week = trendAnalysisView.findViewById(R.id.tv_every_week_value);

        iv_every_month = trendAnalysisView.findViewById(R.id.iv_every_month);
        tv_every_month = trendAnalysisView.findViewById(R.id.tv_every_month_value);

        tv_future_seven_days_value = trendAnalysisView.findViewById(R.id.tv_future_seven_days_value);
        tv_future_seven_days_date = trendAnalysisView.findViewById(R.id.tv_future_seven_days_date);

        tv_future_end_of_month_value = trendAnalysisView.findViewById(R.id.tv_future_end_of_month_value);
        tv_future_end_of_month_date = trendAnalysisView.findViewById(R.id.tv_future_end_of_month_date);

        tv_future_end_of_year_value = trendAnalysisView.findViewById(R.id.tv_future_end_of_year_value);
        tv_future_end_of_year_date = trendAnalysisView.findViewById(R.id.tv_future_end_of_year_date);

        ll_type_choice.setOnClickListener(this);
        ll_open_details.setOnClickListener(this);
    }

    private void initData() {
        todayTimestamp = CalculationUtils.dateToLong(Utils.getCurrentDate());
        String tmp = Utils.getOldDate(-7);
        sevenDaysAgoTimestamp = CalculationUtils.dateToLong(tmp);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        monthAgoTimestamp = CalculationUtils.dateToLong(year + "年" + month + "月" + 1 + "日");
        //monthAgoTimestamp = CalculationUtils.dateToLong(2017 + "年" + 10 + "月" + 8 + "日");
        yearAgoTimestamp = CalculationUtils.dateToLong(year + "年" + 1 + "月" + 1 + "日");
        // yearAgoTimestamp = CalculationUtils.dateToLong(2017 + "年" + 10 + "月" + 16 + "日");
        RealmResults<BodyData> results = RealmStorageHelper.getInstance()
                .getRealm()
                .where(BodyData.class)
                .equalTo("timeStamp", todayTimestamp)
                .or()
                .equalTo("timeStamp", sevenDaysAgoTimestamp)
                .or()
                .equalTo("timeStamp", monthAgoTimestamp)
                .or()
                .equalTo("timeStamp", yearAgoTimestamp)
                .findAll();
        int size = results.size();
        for (int i = 0; i < size; i++) {
            BodyData data = results.get(i);
            Log.d("results1", "date = " + data.getDate() + ">>>AverageWeight = " + data.getAverageWeight()
                    + ">>>Amweight = " + data.getAmWeight()
                    + ">>>pmweight = " + data.getPmWeight()
                    + ">>>Acvitvity = " + data.getActivity()
                    + ">>>chest = " + data.getChest()
                    + ">>>timtstamp = " + data.getTimeStamp());
            if (data.getTimeStamp() == todayTimestamp) {
                todayBodyData = data;
            } else if (data.getTimeStamp() == sevenDaysAgoTimestamp) {
                sevenDayAgoBodyData = data;
            } else if (data.getTimeStamp() == monthAgoTimestamp) {
                monthAgoBodyData = data;
            } else if (data.getTimeStamp() == yearAgoTimestamp) {
                yearAgoBodyData = data;
            }
        }
        showWeight();
    }

    private void updateUI(float sevenDayDiff, float monthDiff, float yearDiff, float everyDayValue, float everyWeekValue, float everyMonthValue, float futureSevenDayValue, float future30DaysValue, float futureEndOfYearValue, boolean isFutureVisible) {
        tv_last_seven_days.setText(String.valueOf(sevenDayDiff));
        tv_future_seven_days_value.setText(String.valueOf(futureSevenDayValue));
        tv_future_seven_days_date.setText(Utils.getOldDate(7));

        if (sevenDayDiff > 0) {
            iv_last_seven_days.setImageResource(R.drawable.img_trend_value_up);
            iv_everyday.setImageResource(R.drawable.img_trend_value_up);
        } else {
            iv_last_seven_days.setImageResource(R.drawable.img_trend_value_down);
            iv_everyday.setImageResource(R.drawable.img_trend_value_down);
        }
        tv_this_month_to_today.setText(String.valueOf(monthDiff));
        tv_future_end_of_month_value.setText(String.valueOf(future30DaysValue));
        tv_future_end_of_month_date.setText(Utils.getOldDate(30));
        if (monthDiff > 0) {
            iv_this_month_to_today.setImageResource(R.drawable.img_trend_value_up);
            iv_every_week.setImageResource(R.drawable.img_trend_value_up);
        } else {
            iv_this_month_to_today.setImageResource(R.drawable.img_trend_value_down);
            iv_every_week.setImageResource(R.drawable.img_trend_value_down);
        }
        everyDayValue = everyDayValue > 0 ? everyDayValue : -everyDayValue;
        everyWeekValue = everyWeekValue > 0 ? everyWeekValue : -everyWeekValue;
        everyMonthValue = everyMonthValue > 0 ? everyMonthValue : -everyMonthValue;

        tv_everyday.setText(String.valueOf(everyDayValue));
        tv_every_week.setText(String.valueOf(everyWeekValue));
        tv_every_month.setText(String.valueOf(everyMonthValue));

        if (!isFutureVisible) {
            ll_future.setVisibility(GONE);
        } else {
            ll_future.setVisibility(VISIBLE);
            tv_this_year_to_today.setText(String.valueOf(yearDiff));
            tv_future_end_of_year_value.setText(String.valueOf(futureEndOfYearValue));
            tv_future_end_of_year_date.setText(calendar.get(Calendar.YEAR) + "年12月31日");
            if (yearDiff > 0) {
                iv_this_year_to_today.setImageResource(R.drawable.img_trend_value_up);
                iv_every_month.setImageResource(R.drawable.img_trend_value_up);
            } else {
                iv_this_year_to_today.setImageResource(R.drawable.img_trend_value_down);
                iv_every_month.setImageResource(R.drawable.img_trend_value_down);
            }
        }
    }

    private void showWeight() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float futureSevenDayWeight = 0;
        float future30DaysWeight = 0;
        float futureEndOfYearWeight = 0;
        float everyDayWeight = 0;
        float evertMonthWeight = 0;
        float evertWeekWeight = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getAverageWeight() > 0 && sevenDayAgoBodyData.getAverageWeight() > 0) {
                sevenDayDiff = todayBodyData.getAverageWeight() - sevenDayAgoBodyData.getAverageWeight();
                futureSevenDayWeight = todayBodyData.getAverageWeight() + sevenDayDiff;
                everyDayWeight = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getAverageWeight() > 0 && monthAgoBodyData.getAverageWeight() > 0) {
                monthDiff = todayBodyData.getAverageWeight() - monthAgoBodyData.getAverageWeight();
                future30DaysWeight = todayBodyData.getAverageWeight() + monthDiff;
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                evertWeekWeight = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getAverageWeight() > 0 && yearAgoBodyData.getAverageWeight() > 0) {
                yearDiff = todayBodyData.getAverageWeight() - yearAgoBodyData.getAverageWeight();
                int months = calendar.get(Calendar.MONTH) + 1;
                futureEndOfYearWeight = todayBodyData.getAverageWeight() + (yearDiff / months) * (12 - months);
                evertMonthWeight = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayWeight, evertWeekWeight, evertMonthWeight, futureSevenDayWeight, future30DaysWeight, futureEndOfYearWeight, true);
    }

    private void showMorningWeight() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayWeight = 0;
        float evertMonthWeight = 0;
        float evertWeekWeight = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getAmWeight() > 0 && sevenDayAgoBodyData.getAmWeight() > 0) {
                sevenDayDiff = todayBodyData.getAmWeight() - sevenDayAgoBodyData.getAmWeight();
                everyDayWeight = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getAmWeight() > 0 && monthAgoBodyData.getAmWeight() > 0) {
                monthDiff = todayBodyData.getAmWeight() - monthAgoBodyData.getAmWeight();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                evertWeekWeight = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getAmWeight() > 0 && yearAgoBodyData.getAmWeight() > 0) {
                yearDiff = todayBodyData.getAmWeight() - yearAgoBodyData.getAmWeight();
                int months = calendar.get(Calendar.MONTH) + 1;
                evertMonthWeight = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayWeight, evertWeekWeight, evertMonthWeight, 0, 0, 0, false);
    }

    private void showNoonWeight() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayWeight = 0;
        float evertMonthWeight = 0;
        float evertWeekWeight = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getPmWeight() > 0 && sevenDayAgoBodyData.getPmWeight() > 0) {
                sevenDayDiff = todayBodyData.getPmWeight() - sevenDayAgoBodyData.getPmWeight();
                everyDayWeight = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getPmWeight() > 0 && monthAgoBodyData.getPmWeight() > 0) {
                monthDiff = todayBodyData.getPmWeight() - monthAgoBodyData.getPmWeight();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                evertWeekWeight = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getPmWeight() > 0 && yearAgoBodyData.getPmWeight() > 0) {
                yearDiff = todayBodyData.getPmWeight() - yearAgoBodyData.getPmWeight();
                int months = calendar.get(Calendar.MONTH) + 1;
                evertMonthWeight = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayWeight, evertWeekWeight, evertMonthWeight, 0, 0, 0, false);
    }

    private void showNightWeight() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayWeight = 0;
        float evertMonthWeight = 0;
        float evertWeekWeight = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getNightWeight() > 0 && sevenDayAgoBodyData.getNightWeight() > 0) {
                sevenDayDiff = todayBodyData.getNightWeight() - sevenDayAgoBodyData.getNightWeight();
                everyDayWeight = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getNightWeight() > 0 && monthAgoBodyData.getNightWeight() > 0) {
                monthDiff = todayBodyData.getNightWeight() - monthAgoBodyData.getNightWeight();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                evertWeekWeight = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getNightWeight() > 0 && yearAgoBodyData.getNightWeight() > 0) {
                yearDiff = todayBodyData.getNightWeight() - yearAgoBodyData.getNightWeight();
                int months = calendar.get(Calendar.MONTH) + 1;
                evertMonthWeight = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayWeight, evertWeekWeight, evertMonthWeight, 0, 0, 0, false);
    }

    private void showInternalOrgansFat() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayInternalOrgansFat = 0;
        float evertMonthInternalOrgansFat = 0;
        float evertWeekInternalOrgansFat = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getInternalOrgansFat() > 0 && sevenDayAgoBodyData.getInternalOrgansFat() > 0) {
                sevenDayDiff = todayBodyData.getInternalOrgansFat() - sevenDayAgoBodyData.getInternalOrgansFat();
                everyDayInternalOrgansFat = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getInternalOrgansFat() > 0 && monthAgoBodyData.getInternalOrgansFat() > 0) {
                monthDiff = todayBodyData.getInternalOrgansFat() - monthAgoBodyData.getInternalOrgansFat();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                evertWeekInternalOrgansFat = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getInternalOrgansFat() > 0 && yearAgoBodyData.getInternalOrgansFat() > 0) {
                yearDiff = todayBodyData.getInternalOrgansFat() - yearAgoBodyData.getInternalOrgansFat();
                int months = calendar.get(Calendar.MONTH) + 1;
                evertMonthInternalOrgansFat = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayInternalOrgansFat, evertWeekInternalOrgansFat, evertMonthInternalOrgansFat, 0, 0, 0, false);
    }

    private void showMuscle() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayMuscle = 0;
        float everyMonthMuscle = 0;
        float everyWeekMuscle = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getMuscle() > 0 && sevenDayAgoBodyData.getMuscle() > 0) {
                sevenDayDiff = todayBodyData.getMuscle() - sevenDayAgoBodyData.getMuscle();
                everyDayMuscle = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getMuscle() > 0 && monthAgoBodyData.getMuscle() > 0) {
                monthDiff = todayBodyData.getMuscle() - monthAgoBodyData.getMuscle();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekMuscle = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getMuscle() > 0 && yearAgoBodyData.getMuscle() > 0) {
                yearDiff = todayBodyData.getMuscle() - yearAgoBodyData.getMuscle();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthMuscle = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayMuscle, everyWeekMuscle, everyMonthMuscle, 0, 0, 0, false);
    }

    private void showBone() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayBone = 0;
        float everyMonthBone = 0;
        float everyWeekBone = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getBone() > 0 && sevenDayAgoBodyData.getBone() > 0) {
                sevenDayDiff = todayBodyData.getBone() - sevenDayAgoBodyData.getBone();
                everyDayBone = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getBone() > 0 && monthAgoBodyData.getBone() > 0) {
                monthDiff = todayBodyData.getBone() - monthAgoBodyData.getBone();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekBone = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getBone() > 0 && yearAgoBodyData.getBone() > 0) {
                yearDiff = todayBodyData.getBone() - yearAgoBodyData.getBone();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthBone = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayBone, everyWeekBone, everyMonthBone, 0, 0, 0, false);
    }

    private void showHeartRate() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayHeartRate = 0;
        float everyMonthHeartRate = 0;
        float everyWeekHeartRate = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getHeartRate() > 0 && sevenDayAgoBodyData.getHeartRate() > 0) {
                sevenDayDiff = todayBodyData.getHeartRate() - sevenDayAgoBodyData.getHeartRate();
                everyDayHeartRate = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getHeartRate() > 0 && monthAgoBodyData.getHeartRate() > 0) {
                monthDiff = todayBodyData.getHeartRate() - monthAgoBodyData.getHeartRate();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekHeartRate = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getHeartRate() > 0 && yearAgoBodyData.getHeartRate() > 0) {
                yearDiff = todayBodyData.getHeartRate() - yearAgoBodyData.getHeartRate();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthHeartRate = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayHeartRate, everyWeekHeartRate, everyMonthHeartRate, 0, 0, 0, false);
    }

    private void showHeartBodyMoisture() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayBodyMoisture = 0;
        float everyMonthBodyMoisture = 0;
        float everyWeekBodyMoisture = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getBodyMoisture() > 0 && sevenDayAgoBodyData.getBodyMoisture() > 0) {
                sevenDayDiff = todayBodyData.getBodyMoisture() - sevenDayAgoBodyData.getBodyMoisture();
                everyDayBodyMoisture = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getBodyMoisture() > 0 && monthAgoBodyData.getBodyMoisture() > 0) {
                monthDiff = todayBodyData.getBodyMoisture() - monthAgoBodyData.getBodyMoisture();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekBodyMoisture = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getBodyMoisture() > 0 && yearAgoBodyData.getBodyMoisture() > 0) {
                yearDiff = todayBodyData.getBodyMoisture() - yearAgoBodyData.getBodyMoisture();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthBodyMoisture = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayBodyMoisture, everyWeekBodyMoisture, everyMonthBodyMoisture, 0, 0, 0, false);
    }

    private void showBiceps() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayBiceps = 0;
        float everyMonthBiceps = 0;
        float everyWeekBiceps = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getBiceps() > 0 && sevenDayAgoBodyData.getBiceps() > 0) {
                sevenDayDiff = todayBodyData.getBiceps() - sevenDayAgoBodyData.getBiceps();
                everyDayBiceps = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getBiceps() > 0 && monthAgoBodyData.getBiceps() > 0) {
                monthDiff = todayBodyData.getBiceps() - monthAgoBodyData.getBiceps();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekBiceps = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getBiceps() > 0 && yearAgoBodyData.getBiceps() > 0) {
                yearDiff = todayBodyData.getBiceps() - yearAgoBodyData.getBiceps();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthBiceps = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayBiceps, everyWeekBiceps, everyMonthBiceps, 0, 0, 0, false);
    }

    private void showBust() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayBust = 0;
        float everyMonthBust = 0;
        float everyWeekBust = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getBust() > 0 && sevenDayAgoBodyData.getBust() > 0) {
                sevenDayDiff = todayBodyData.getBust() - sevenDayAgoBodyData.getBust();
                everyDayBust = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getBust() > 0 && monthAgoBodyData.getBust() > 0) {
                monthDiff = todayBodyData.getBust() - monthAgoBodyData.getBust();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekBust = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getBust() > 0 && yearAgoBodyData.getBust() > 0) {
                yearDiff = todayBodyData.getBust() - yearAgoBodyData.getBust();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthBust = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayBust, everyWeekBust, everyMonthBust, 0, 0, 0, false);
    }

    private void showWaist() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayWaist = 0;
        float everyMonthWaist = 0;
        float everyWeekWaist = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getWaist() > 0 && sevenDayAgoBodyData.getWaist() > 0) {
                sevenDayDiff = todayBodyData.getWaist() - sevenDayAgoBodyData.getWaist();
                everyDayWaist = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getWaist() > 0 && monthAgoBodyData.getWaist() > 0) {
                monthDiff = todayBodyData.getWaist() - monthAgoBodyData.getWaist();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekWaist = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getWaist() > 0 && yearAgoBodyData.getWaist() > 0) {
                yearDiff = todayBodyData.getWaist() - yearAgoBodyData.getWaist();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthWaist = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayWaist, everyWeekWaist, everyMonthWaist, 0, 0, 0, false);
    }

    private void showButtocks() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayButtocks = 0;
        float everyMonthButtocks = 0;
        float everyWeekButtocks = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getButtocks() > 0 && sevenDayAgoBodyData.getButtocks() > 0) {
                sevenDayDiff = todayBodyData.getButtocks() - sevenDayAgoBodyData.getButtocks();
                everyDayButtocks = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getButtocks() > 0 && monthAgoBodyData.getButtocks() > 0) {
                monthDiff = todayBodyData.getButtocks() - monthAgoBodyData.getButtocks();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekButtocks = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getButtocks() > 0 && yearAgoBodyData.getButtocks() > 0) {
                yearDiff = todayBodyData.getButtocks() - yearAgoBodyData.getButtocks();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthButtocks = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayButtocks, everyWeekButtocks, everyMonthButtocks, 0, 0, 0, false);
    }

    private void showThigh() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayThigh = 0;
        float everyMonthThigh = 0;
        float everyWeekThigh = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getThigh() > 0 && sevenDayAgoBodyData.getThigh() > 0) {
                sevenDayDiff = todayBodyData.getThigh() - sevenDayAgoBodyData.getThigh();
                everyDayThigh = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getThigh() > 0 && monthAgoBodyData.getThigh() > 0) {
                monthDiff = todayBodyData.getThigh() - monthAgoBodyData.getThigh();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekThigh = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getThigh() > 0 && yearAgoBodyData.getThigh() > 0) {
                yearDiff = todayBodyData.getThigh() - yearAgoBodyData.getThigh();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthThigh = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayThigh, everyWeekThigh, everyMonthThigh, 0, 0, 0, false);
    }

    private void showForearm() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayForearm = 0;
        float everyMonthForearm = 0;
        float everyWeekForearm = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getForearm() > 0 && sevenDayAgoBodyData.getForearm() > 0) {
                sevenDayDiff = todayBodyData.getForearm() - sevenDayAgoBodyData.getForearm();
                everyDayForearm = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getForearm() > 0 && monthAgoBodyData.getForearm() > 0) {
                monthDiff = todayBodyData.getForearm() - monthAgoBodyData.getForearm();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekForearm = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getForearm() > 0 && yearAgoBodyData.getForearm() > 0) {
                yearDiff = todayBodyData.getForearm() - yearAgoBodyData.getForearm();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthForearm = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayForearm, everyWeekForearm, everyMonthForearm, 0, 0, 0, false);
    }

    private void showAbdomen() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayAbdomen = 0;
        float everyMonthAbdomen = 0;
        float everyWeekAbdomen = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getAbdomen() > 0 && sevenDayAgoBodyData.getAbdomen() > 0) {
                sevenDayDiff = todayBodyData.getAbdomen() - sevenDayAgoBodyData.getAbdomen();
                everyDayAbdomen = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getAbdomen() > 0 && monthAgoBodyData.getAbdomen() > 0) {
                monthDiff = todayBodyData.getAbdomen() - monthAgoBodyData.getAbdomen();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekAbdomen = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getAbdomen() > 0 && yearAgoBodyData.getAbdomen() > 0) {
                yearDiff = todayBodyData.getAbdomen() - yearAgoBodyData.getAbdomen();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthAbdomen = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayAbdomen, everyWeekAbdomen, everyMonthAbdomen, 0, 0, 0, false);
    }

    private void showWrist() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayWrist = 0;
        float everyMonthWrist = 0;
        float everyWeekWrist = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getWrist() > 0 && sevenDayAgoBodyData.getWrist() > 0) {
                sevenDayDiff = todayBodyData.getWrist() - sevenDayAgoBodyData.getWrist();
                everyDayWrist = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getWrist() > 0 && monthAgoBodyData.getWrist() > 0) {
                monthDiff = todayBodyData.getWrist() - monthAgoBodyData.getWrist();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekWrist = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getWrist() > 0 && yearAgoBodyData.getWrist() > 0) {
                yearDiff = todayBodyData.getWrist() - yearAgoBodyData.getWrist();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthWrist = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayWrist, everyWeekWrist, everyMonthWrist, 0, 0, 0, false);
    }

    private void showChest() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayChest = 0;
        float everyMonthChest = 0;
        float everyWeekChest = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getChest() > 0 && sevenDayAgoBodyData.getChest() > 0) {
                sevenDayDiff = todayBodyData.getChest() - sevenDayAgoBodyData.getChest();
                everyDayChest = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getChest() > 0 && monthAgoBodyData.getChest() > 0) {
                monthDiff = todayBodyData.getChest() - monthAgoBodyData.getChest();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekChest = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getChest() > 0 && yearAgoBodyData.getChest() > 0) {
                yearDiff = todayBodyData.getChest() - yearAgoBodyData.getChest();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthChest = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayChest, everyWeekChest, everyMonthChest, 0, 0, 0, false);
    }

    private void showNeck() {
        float sevenDayDiff = 0;
        float monthDiff = 0;
        float yearDiff = 0;
        float everyDayNeck = 0;
        float everyMonthNeck = 0;
        float everyWeekNeck = 0;
        if (todayBodyData != null && sevenDayAgoBodyData != null) {
            if (todayBodyData.getNeck() > 0 && sevenDayAgoBodyData.getNeck() > 0) {
                sevenDayDiff = todayBodyData.getNeck() - sevenDayAgoBodyData.getNeck();
                everyDayNeck = sevenDayDiff / 7;
            }
        }
        if (todayBodyData != null && monthAgoBodyData != null) {
            if (todayBodyData.getNeck() > 0 && monthAgoBodyData.getNeck() > 0) {
                monthDiff = todayBodyData.getNeck() - monthAgoBodyData.getNeck();
                int days = (int) (todayTimestamp - monthAgoTimestamp);
                int week = days / 7;
                everyWeekNeck = monthDiff / week;
            }
        }
        if (todayBodyData != null && yearAgoBodyData != null) {
            if (todayBodyData.getNeck() > 0 && yearAgoBodyData.getNeck() > 0) {
                yearDiff = todayBodyData.getNeck() - yearAgoBodyData.getNeck();
                int months = calendar.get(Calendar.MONTH) + 1;
                everyMonthNeck = yearDiff / months;
            }
        }
        updateUI(sevenDayDiff, monthDiff, yearDiff, everyDayNeck, everyWeekNeck, everyMonthNeck, 0, 0, 0, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_type_choice:
                showTrendTypeDialog();
                break;
            case R.id.ll_open_details:
                showTrendDetailsActivity();
                addData();
                break;
        }
    }


    public void showTrendDetailsActivity() {
        Intent intent = new Intent();
        intent.setClass(context, TrendDetailsActivity.class);
        context.startActivity(intent);
    }


    /**
     * 查询到最近一年的数据
     */
    private List<BodyData> getBodyDataList() {
        if (bodyDataList == null) {
            bodyDataList = new ArrayList<>();
        }
        RealmResults<BodyData> results = RealmStorageHelper.getInstance().getRealm().where(BodyData.class).greaterThan("timeStamp", 20171011).findAll();

        int size = results.size();
        for (int i = 0; i < size; i++) {
            BodyData data = results.get(i);
            Log.d("results1", "date = " + data.getDate() + ">>>AverageWeight = " + data.getAverageWeight()
                    + ">>>Amweight = " + data.getAmWeight()
                    + ">>>pmweight = " + data.getPmWeight()
                    + ">>>Acvitvity = " + data.getActivity()
                    + ">>>chest = " + data.getChest()
                    + ">>>timtstamp = " + data.getTimeStamp());
        }
        return bodyDataList;
    }

    private void addData() {
        List<BodyData> bodyDataList = new ArrayList<>();
        for (int i = 6; i < 27; i++) {
            BodyData bodyData = new BodyData();
            bodyData.setDate("2017年10月" + i + "日");
            bodyData.setAmWeight(70 + i);
            bodyData.setPmWeight(i);
            bodyData.setAverageWeight(70 + i);
            bodyData.setHeartRate(70 + i);
            bodyData.setChest(70 + 0.1f * i);
            bodyData.setBone(50 - 0.1f * i);

            bodyDataList.add(bodyData);
        }
        RealmStorageHelper.getInstance().insertBodyDataSync(bodyDataList);
    }


    private void showTrendTypeDialog() {
        CustomDialog.TrendBuilder builder = new CustomDialog.TrendBuilder(context);
        builder.setOnDialogClickListener(new DialogClickListener1() {
            @Override
            public void OnConfirmed(Object obj) {
                int id = (int) obj;
                switch (id) {
                    case R.id.tv_trend_weight:
                        showWeight();
                        break;
                    case R.id.tv_trend_am_weight:
                        showMorningWeight();
                        break;
                    case R.id.tv_trend_pm_weight:
                        showNoonWeight();
                        break;
                    case R.id.tv_trend_night_weight:
                        showNightWeight();
                        break;
                    case R.id.tv_trend_internalOrgansFat:
                        showInternalOrgansFat();
                        break;
                    case R.id.tv_trend_muscle:
                        showMuscle();
                        break;
                    case R.id.tv_trend_bone:
                        showBone();
                        break;
                    case R.id.tv_trend_heart_rate:
                        showHeartRate();
                        break;
                    case R.id.tv_trend_bodyMoisture:
                        showHeartBodyMoisture();
                        break;
                    case R.id.tv_trend_biceps:
                        showBiceps();
                        break;
                    case R.id.tv_trend_bust:
                        showBust();
                        break;
                    case R.id.tv_trend_waist:
                        showWaist();
                        break;
                    case R.id.tv_trend_buttocks:
                        showButtocks();
                        break;
                    case R.id.tv_trend_thigh:
                        showThigh();
                        break;
                    case R.id.tv_trend_forearm:
                        showForearm();
                        break;
                    case R.id.tv_trend_abdomen:
                        showAbdomen();
                        break;
                    case R.id.tv_trend_wrist:
                        showWrist();
                        break;
                    case R.id.tv_trend_chest:
                        showChest();
                        break;
                    case R.id.tv_trend_neck:
                        showNeck();
                        break;
                }
            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = Utils.setCostumeDialogStyle(builder.create(), context, 0.5f, 0.65f, 0, 0, Gravity.RIGHT | Gravity.BOTTOM, 0);
        Window window = dialog.getWindow();
        if (window != null)
            window.setWindowAnimations(R.style.trend_animation_style);
        dialog.show();
    }

}
