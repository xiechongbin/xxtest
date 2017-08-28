package com.xiaoxie.weightrecord.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.xiaoxie.weightrecord.R;

/**
 * desc:提醒设置
 * Created by xiaoxie on 2017/8/28.
 */
public class ReminderSettingFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rl_reminder_repeat;
    private RelativeLayout rl_choose_time;
    private RelativeLayout rl_choose_day;
    private RelativeLayout rl_reminder_onetime;
    private RelativeLayout rl_current_date;
    private RelativeLayout rl_current_time;

    private ToggleButton tb_reminder_repeat;
    private ToggleButton tb_reminder_onetime;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_remindersetting_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rl_reminder_repeat = view.findViewById(R.id.rl_reminder_repeat);
        rl_choose_time = view.findViewById(R.id.rl_choose_time);
        rl_choose_day = view.findViewById(R.id.rl_choose_day);
        rl_reminder_onetime = view.findViewById(R.id.rl_reminder_onetime);
        rl_current_date = view.findViewById(R.id.rl_current_date);
        rl_current_time = view.findViewById(R.id.rl_current_time);

        tb_reminder_repeat = view.findViewById(R.id.tb_reminder_repeat);
        tb_reminder_onetime = view.findViewById(R.id.tb_reminder_onetime);

        rl_choose_time.setOnClickListener(this);
        rl_choose_day.setOnClickListener(this);
        rl_current_date.setOnClickListener(this);
        rl_current_time.setOnClickListener(this);

        tb_reminder_repeat.setOnClickListener(this);
        tb_reminder_onetime.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        // TODO: 2017/8/28
    }
}
