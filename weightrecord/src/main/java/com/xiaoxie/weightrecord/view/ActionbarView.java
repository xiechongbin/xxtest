package com.xiaoxie.weightrecord.view;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.activity.SettingsActivity;
import com.xiaoxie.weightrecord.fragment.CalendarFragment;
import com.xiaoxie.weightrecord.fragment.LogFragment;
import com.xiaoxie.weightrecord.fragment.PhotoFragment;
import com.xiaoxie.weightrecord.fragment.ResourceFragment;
import com.xiaoxie.weightrecord.fragment.WeightFragment;
import com.xiaoxie.weightrecord.interfaces.ActionBarClickListener;

import java.util.Calendar;
import java.util.Date;

/**
 * desc:自定义导航栏view
 * Created by xiaoxie on 2017/8/23.
 */
public class ActionbarView extends LinearLayout implements View.OnClickListener {

    private ImageView imageView_menu;
    private ImageView imageView_notification;
    private ImageView imageView_cloud;
    private ImageView imageView_personal_center;

    private ImageView imageView_food;
    private ImageView imageView_challenge;
    private ImageView imageView_history;
    private ImageView imageView_eyes;

    private ImageView imageView_backup_or_repair;


    private TextView tv_title;

    private LinearLayout ll_actionbar_weight;
    private LinearLayout ll_actionbar_calendar;
    private LinearLayout ll_actionbar_log;

    private ActionBarClickListener clickListener;

    public ActionbarView(Context context) {
        super(context);
        initView(context);
    }

    public ActionbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ActionbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化控件
     */
    private void initView(Context context) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View v = View.inflate(context, R.layout.layout_actionbar, null);//消除警告
        this.addView(v, layoutParams);
        imageView_menu = v.findViewById(R.id.img_menu);
        imageView_notification = v.findViewById(R.id.img_notification);
        imageView_cloud = v.findViewById(R.id.img_cloud);
        imageView_personal_center = v.findViewById(R.id.img_personal_center);

        imageView_food = v.findViewById(R.id.img_food);
        imageView_challenge = v.findViewById(R.id.img_challenge);
        imageView_history = v.findViewById(R.id.img_history);
        imageView_eyes = v.findViewById(R.id.img_eyes);

        tv_title = v.findViewById(R.id.tv_title);

        ll_actionbar_weight = v.findViewById(R.id.ll_actionbar_weight);
        ll_actionbar_calendar = v.findViewById(R.id.ll_actionbar_calendar);
        ll_actionbar_log = v.findViewById(R.id.ll_actionbar_log);

        imageView_menu.setOnClickListener(this);
        imageView_notification.setOnClickListener(this);
        imageView_cloud.setOnClickListener(this);
        imageView_personal_center.setOnClickListener(this);
        imageView_food.setOnClickListener(this);
        imageView_challenge.setOnClickListener(this);
        imageView_history.setOnClickListener(this);
        imageView_eyes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null) {
            clickListener.onActionBarClick(view.getId());
        }
    }

    public void setOnActionBarClickListener(ActionBarClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 设置该显示的view
     */
    public void setWitchToShow(String fragmentName) {
        if (TextUtils.isEmpty(fragmentName))
            return;
        if (fragmentName.equals(WeightFragment.class.getSimpleName())) {
            ll_actionbar_weight.setVisibility(VISIBLE);
            ll_actionbar_calendar.setVisibility(GONE);
            ll_actionbar_log.setVisibility(GONE);
            tv_title.setText(getCurrentDate());//设置日期
        } else if (fragmentName.equals(PhotoFragment.class.getSimpleName())) {
            ll_actionbar_weight.setVisibility(GONE);
            ll_actionbar_calendar.setVisibility(GONE);
            ll_actionbar_log.setVisibility(GONE);
            tv_title.setText(R.string.title_body_change);
        } else if (fragmentName.equals(LogFragment.class.getSimpleName())) {
            ll_actionbar_weight.setVisibility(GONE);
            ll_actionbar_calendar.setVisibility(GONE);
            ll_actionbar_log.setVisibility(VISIBLE);
            tv_title.setText(R.string.label_Log);
        } else if (fragmentName.equals(CalendarFragment.class.getSimpleName())) {
            ll_actionbar_weight.setVisibility(GONE);
            ll_actionbar_calendar.setVisibility(VISIBLE);
            ll_actionbar_log.setVisibility(GONE);
            tv_title.setText(R.string.title_calendar);
        } else if (fragmentName.equals(ResourceFragment.class.getSimpleName())) {
            ll_actionbar_weight.setVisibility(GONE);
            ll_actionbar_calendar.setVisibility(GONE);
            ll_actionbar_log.setVisibility(GONE);
            tv_title.setText(R.string.label_resource);
        } else if (fragmentName.equals(SettingsActivity.class.getSimpleName())) {
            ll_actionbar_weight.setVisibility(GONE);
            ll_actionbar_calendar.setVisibility(GONE);
            ll_actionbar_log.setVisibility(GONE);
            tv_title.setText(R.string.label_sb_setting);
        }
        invalidate();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd日");
        Date date = calendar.getTime();
        return format.format(date);
    }
}
