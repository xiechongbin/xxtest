package com.xiaoxie.weightrecord.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;

import java.util.Calendar;

/**
 * desc:提醒设置
 * Created by xiaoxie on 2017/8/28.
 */
public class ReminderSettingFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvCurrentTime;
    private TextView tvCurrentDate;

    private TextView tvReminderTime;
    private TextView tvReminderDay;

    private ToggleButton tb_reminder_repeat;
    private ToggleButton tb_reminder_onetime;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_remindersetting_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        RelativeLayout rl_choose_time = view.findViewById(R.id.rl_choose_time);
        RelativeLayout rl_choose_day = view.findViewById(R.id.rl_choose_day);
        RelativeLayout rl_current_date = view.findViewById(R.id.rl_current_date);
        RelativeLayout rl_current_time = view.findViewById(R.id.rl_current_time);

        tb_reminder_repeat = view.findViewById(R.id.tb_reminder_repeat);
        tb_reminder_onetime = view.findViewById(R.id.tb_reminder_onetime);

        tvCurrentTime = view.findViewById(R.id.tv_reminder_current_time);
        tvCurrentDate = view.findViewById(R.id.tv_reminder_current_date);

        tvReminderTime = view.findViewById(R.id.tv_reminder_time);
        tvReminderDay = view.findViewById(R.id.tv_reminder_day);

        rl_choose_time.setOnClickListener(this);
        rl_choose_day.setOnClickListener(this);
        rl_current_date.setOnClickListener(this);
        rl_current_time.setOnClickListener(this);

        tb_reminder_repeat.setOnClickListener(this);
        tb_reminder_onetime.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String[] times = getCuttentTime();
        if (times != null) {
            tvCurrentTime.setText(times[6] + times[4] + ":" + times[5]);
            tvCurrentDate.setText("周" + times[3] + "," + times[1] + "月" + times[2] + "," + times[0]);
        }
        SharePrefenceUtils.setBoolean(getActivity(), SharePrefenceUtils.KEY_REMINDER_REPEAT, false);
        tb_reminder_onetime.setChecked(SharePrefenceUtils.getBoolean(getActivity(), SharePrefenceUtils.KEY_REMINDER_ONTTIME, false));
        tb_reminder_repeat.setChecked(SharePrefenceUtils.getBoolean(getActivity(), SharePrefenceUtils.KEY_REMINDER_REPEAT, false));
        tvReminderDay.setText(SharePrefenceUtils.getString(getActivity(),SharePrefenceUtils.KEY_REMINDER_WHICH_DAY,""));
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_reminder_onetime:
                if (tb_reminder_onetime.isChecked()) {
                    SharePrefenceUtils.setBoolean(getActivity(), SharePrefenceUtils.KEY_REMINDER_ONTTIME, true);
                } else {
                    SharePrefenceUtils.setBoolean(getActivity(), SharePrefenceUtils.KEY_REMINDER_ONTTIME, false);
                }
                break;
            case R.id.tb_reminder_repeat:
                if (tb_reminder_repeat.isChecked()) {
                    SharePrefenceUtils.setBoolean(getActivity(), SharePrefenceUtils.KEY_REMINDER_REPEAT, true);
                } else {
                    SharePrefenceUtils.setBoolean(getActivity(), SharePrefenceUtils.KEY_REMINDER_REPEAT, false);
                }
                break;
            case R.id.rl_choose_time:
                showTimeSelectDialog();
                break;
            case R.id.rl_choose_day:
                showWeekSelectDialog();
                break;
        }
    }

    /**
     * 获取当前时间
     */
    private String[] getCuttentTime() {
        String[] times = new String[7];
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String dayOfWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(dayOfWeek)) {
            dayOfWeek = "天";
        } else if ("2".equals(dayOfWeek)) {
            dayOfWeek = "一";
        } else if ("3".equals(dayOfWeek)) {
            dayOfWeek = "二";
        } else if ("4".equals(dayOfWeek)) {
            dayOfWeek = "三";
        } else if ("5".equals(dayOfWeek)) {
            dayOfWeek = "四";
        } else if ("6".equals(dayOfWeek)) {
            dayOfWeek = "五";
        } else if ("7".equals(dayOfWeek)) {
            dayOfWeek = "六";
        }
        String timeOfDay = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        int am_pm = calendar.get(Calendar.AM_PM);

        times[0] = year;
        times[1] = month;
        times[2] = day;
        times[3] = dayOfWeek;
        times[4] = timeOfDay;
        times[5] = minute;

        if (am_pm == 0) {
            times[6] = "上午";
        } else {
            times[6] = "下午";
        }

        return times;
    }

    private void showTimeSelectDialog() {
        final CustomDialog.TimeBuilder builder = new CustomDialog.TimeBuilder(getActivity());
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                tvReminderTime.setText(str);
                SharePrefenceUtils.setString(getActivity(), SharePrefenceUtils.KEY_REMINDER_TIME, str);
            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void showWeekSelectDialog() {
        final CustomDialog.WeekBuilder builder = new CustomDialog.WeekBuilder(getActivity());
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                tvReminderDay.setText(str);
                SharePrefenceUtils.setString(getActivity(), SharePrefenceUtils.KEY_REMINDER_WHICH_DAY, str);
            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}
