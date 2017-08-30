package com.xiaoxie.weightrecord.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.fragment.CalendarFragment;
import com.xiaoxie.weightrecord.fragment.LogFragment;
import com.xiaoxie.weightrecord.fragment.PhotoFragment;
import com.xiaoxie.weightrecord.fragment.ResourceFragment;
import com.xiaoxie.weightrecord.fragment.WeightFragment;
import com.xiaoxie.weightrecord.interfaces.ActionBarClickListener;
import com.xiaoxie.weightrecord.interfaces.SlidingMenuClickListener;
import com.xiaoxie.weightrecord.utils.FragmentUtils;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.ActionbarView;
import com.xiaoxie.weightrecord.view.SlidingMenuView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ActionBarClickListener, SlidingMenuClickListener {
    private SlidingMenu slidingMenu;
    private LinearLayout ll_layout_bottomRar;
    private LinearLayout bottomBar_ll_weight;
    private LinearLayout bottomBar_ll_photo;
    private LinearLayout bottomBar_ll_log;
    private LinearLayout bottomBar_ll_calendar;
    private LinearLayout bottomBar_ll_resource;

    private ImageView img_bottom_weight;
    private ImageView img_bottom_photo;
    private ImageView img_bottom_log;
    private ImageView img_bottom_resource;
    private ImageView img_bottom_calendar;

    private TextView tv_bottom_weight;
    private TextView tv_bottom_photo;
    private TextView tv_bottom_log;
    private TextView tv_bottom_calendar;
    private TextView tv_bottom_resource;

    private Fragment weightFragment;
    private Fragment photoFragment;
    private Fragment logFragment;
    private Fragment calendarFragment;
    private Fragment resourceFragment;
    private ActionBar actionBar;
    private ActionbarView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_IS_FIRST_LOAD, true)) {
            Intent intent = new Intent();
            intent.setClass(this, introActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //如果是已经设置过密码 且已经解锁成功则进入解锁界面
        if (SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_HAS_PASSWORD, false) && getIntent().getBooleanExtra("unLockSuccess", true)) {
            Intent intent1 = new Intent();
            intent1.setClass(this, LockPatternActivity.class);
            startActivity(intent1);
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        setCustomActionBar();
        initViews();
        initSlidMenu();
        initWeightFragment();//activity起来默认进入第一个fragment
    }

    /**
     * 自定义标题栏
     */
    private void setCustomActionBar() {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        view = new ActionbarView(this);
        actionBar = getSupportActionBar();
        actionBar.setCustomView(view, layoutParams);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        view.setOnActionBarClickListener(this);
    }

    /**
     * 初始化侧边栏
     */
    private void initSlidMenu() {
        slidingMenu = new SlidingMenu(this);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidth(100);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.setFadeDegree(0.35f);
        int width = wm.getDefaultDisplay().getWidth();
        slidingMenu.setBehindOffset(width / 5);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        //slidingMenu.setMenu(R.layout.layout_slidingmenu);
        SlidingMenuView view = new SlidingMenuView(this);
        view.setOnSildingMenuClickListener(this);
        slidingMenu.setMenu(view);
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        ll_layout_bottomRar = (LinearLayout) findViewById(R.id.ll_layout_bottomRar);
        bottomBar_ll_weight = (LinearLayout) findViewById(R.id.bottomBar_ll_weight);
        bottomBar_ll_photo = (LinearLayout) findViewById(R.id.bottomBar_ll_photo);
        bottomBar_ll_calendar = (LinearLayout) findViewById(R.id.bottomBar_ll_calendar);
        bottomBar_ll_log = (LinearLayout) findViewById(R.id.bottomBar_ll_log);
        bottomBar_ll_resource = (LinearLayout) findViewById(R.id.bottomBar_ll_resource);

        img_bottom_weight = (ImageView) findViewById(R.id.img_bottom_weight);
        img_bottom_photo = (ImageView) findViewById(R.id.img_bottom_photo);
        img_bottom_log = (ImageView) findViewById(R.id.img_bottom_log);
        img_bottom_calendar = (ImageView) findViewById(R.id.img_bottom_calendar);
        img_bottom_resource = (ImageView) findViewById(R.id.img_bottom_resource);

        tv_bottom_weight = (TextView) findViewById(R.id.tv_bottom_weight);
        tv_bottom_log = (TextView) findViewById(R.id.tv_bottom_log);
        tv_bottom_calendar = (TextView) findViewById(R.id.tv_bottom_calendar);
        tv_bottom_photo = (TextView) findViewById(R.id.tv_bottom_photo);
        tv_bottom_resource = (TextView) findViewById(R.id.tv_bottom_resource);

        bottomBar_ll_weight.setOnClickListener(this);
        bottomBar_ll_photo.setOnClickListener(this);
        bottomBar_ll_calendar.setOnClickListener(this);
        bottomBar_ll_log.setOnClickListener(this);
        bottomBar_ll_resource.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        setSelectStyle(id);
        switch (id) {
            case R.id.bottomBar_ll_weight:
                initWeightFragment();
                break;
            case R.id.bottomBar_ll_photo:
                initPhotoFragment();
                break;
            case R.id.bottomBar_ll_calendar:
                initCalendarFragment();
                break;
            case R.id.bottomBar_ll_log:
                initLogFragment();
                break;
            case R.id.bottomBar_ll_resource:
                initResourceFragment();
                break;
        }
    }

    private void initWeightFragment() {
        if (weightFragment == null) {
            weightFragment = new WeightFragment();
        }
        if (weightFragment.isAdded()) {
            FragmentUtils.hideFragment(this, getShowingFragment());
            FragmentUtils.showFragment(this, weightFragment);
        } else {
            FragmentUtils.addFragment(this, R.id.fm_fragment_container, weightFragment, WeightFragment.class.getSimpleName());
        }
        view.setWitchToShow(WeightFragment.class.getSimpleName());
    }

    private void initPhotoFragment() {
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
        }
        if (photoFragment.isAdded()) {
            FragmentUtils.hideFragment(this, getShowingFragment());
            FragmentUtils.showFragment(this, photoFragment);
        } else {
            FragmentUtils.addFragment(this, R.id.fm_fragment_container, photoFragment, PhotoFragment.class.getSimpleName());
        }
        view.setWitchToShow(PhotoFragment.class.getSimpleName());
    }

    private void initLogFragment() {
        if (logFragment == null) {
            logFragment = new LogFragment();
        }
        if (logFragment.isAdded()) {
            FragmentUtils.hideFragment(this, getShowingFragment());
            FragmentUtils.showFragment(this, logFragment);
        } else {
            FragmentUtils.addFragment(this, R.id.fm_fragment_container, logFragment, LogFragment.class.getSimpleName());
        }
        view.setWitchToShow(LogFragment.class.getSimpleName());
    }

    private void initCalendarFragment() {
        if (calendarFragment == null) {
            calendarFragment = new CalendarFragment();
        }
        if (calendarFragment.isAdded()) {
            FragmentUtils.hideFragment(this, getShowingFragment());
            FragmentUtils.showFragment(this, calendarFragment);
        } else {
            FragmentUtils.addFragment(this, R.id.fm_fragment_container, calendarFragment, CalendarFragment.class.getSimpleName());
        }
        view.setWitchToShow(CalendarFragment.class.getSimpleName());
    }

    private void initResourceFragment() {
        if (resourceFragment == null) {
            resourceFragment = new ResourceFragment();
        }
        if (resourceFragment.isAdded()) {
            FragmentUtils.hideFragment(this, getShowingFragment());
            FragmentUtils.showFragment(this, resourceFragment);
        } else {
            FragmentUtils.addFragment(this, R.id.fm_fragment_container, resourceFragment, ResourceFragment.class.getSimpleName());
        }
        view.setWitchToShow(ResourceFragment.class.getSimpleName());
    }

    private Fragment getShowingFragment() {
        if (resourceFragment != null && resourceFragment.isVisible()) {
            return resourceFragment;
        } else if (weightFragment != null && weightFragment.isVisible()) {
            return weightFragment;
        } else if (logFragment != null && logFragment.isVisible()) {
            return logFragment;
        } else if (photoFragment != null && photoFragment.isVisible()) {
            return photoFragment;
        } else {
            return calendarFragment;
        }
    }

    /**
     * 展开或者关闭
     */
    private void hideOrShowSlidingMenu() {
        if (slidingMenu != null) {
            if (!slidingMenu.isMenuShowing()) {
                slidingMenu.showMenu();
            } else {
                slidingMenu.toggle();
            }
        }
    }

    /**
     * 导航栏点击事件回调
     */
    @Override
    public void onActionBarClick(int id) {
        switch (id) {
            case R.id.img_menu:
                hideOrShowSlidingMenu();
                break;
            case R.id.img_notification:
                Toast.makeText(this, "img_notification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_cloud:
                Toast.makeText(this, "img_cloud", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_personal_center:
                Toast.makeText(this, "img_more", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setSelectStyle(int id) {

        switch (id) {
            case R.id.bottomBar_ll_weight:
                img_bottom_weight.setImageResource(R.drawable.ic_weight_pressed);
                img_bottom_log.setImageResource(R.drawable.ic_log_normal);
                img_bottom_calendar.setImageResource(R.drawable.ic_calendar_normal);
                img_bottom_photo.setImageResource(R.drawable.ic_photo_normal);
                img_bottom_resource.setImageResource(R.drawable.ic_resource_normal);

                tv_bottom_weight.setTextColor(Utils.getColor(this, R.color.color_1296db));
                tv_bottom_log.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_photo.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_calendar.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_resource.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));

                break;
            case R.id.bottomBar_ll_photo:
                img_bottom_weight.setImageResource(R.drawable.ic_weight_normal);
                img_bottom_log.setImageResource(R.drawable.ic_log_normal);
                img_bottom_calendar.setImageResource(R.drawable.ic_calendar_normal);
                img_bottom_photo.setImageResource(R.drawable.ic_photo_pressed);
                img_bottom_resource.setImageResource(R.drawable.ic_resource_normal);

                tv_bottom_weight.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_log.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_photo.setTextColor(Utils.getColor(this, R.color.color_1296db));
                tv_bottom_calendar.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_resource.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                break;
            case R.id.bottomBar_ll_calendar:
                img_bottom_weight.setImageResource(R.drawable.ic_weight_normal);
                img_bottom_log.setImageResource(R.drawable.ic_log_normal);
                img_bottom_calendar.setImageResource(R.drawable.ic_calendar_pressed);
                img_bottom_photo.setImageResource(R.drawable.ic_photo_normal);
                img_bottom_resource.setImageResource(R.drawable.ic_resource_normal);

                tv_bottom_weight.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_log.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_photo.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_calendar.setTextColor(Utils.getColor(this, R.color.color_1296db));
                tv_bottom_resource.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                break;
            case R.id.bottomBar_ll_log:
                img_bottom_weight.setImageResource(R.drawable.ic_weight_normal);
                img_bottom_log.setImageResource(R.drawable.ic_log_pressed);
                img_bottom_calendar.setImageResource(R.drawable.ic_calendar_normal);
                img_bottom_photo.setImageResource(R.drawable.ic_photo_normal);
                img_bottom_resource.setImageResource(R.drawable.ic_resource_normal);

                tv_bottom_weight.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_log.setTextColor(Utils.getColor(this, R.color.color_1296db));
                tv_bottom_photo.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_calendar.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_resource.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                break;
            case R.id.bottomBar_ll_resource:
                img_bottom_weight.setImageResource(R.drawable.ic_weight_normal);
                img_bottom_log.setImageResource(R.drawable.ic_log_normal);
                img_bottom_calendar.setImageResource(R.drawable.ic_calendar_normal);
                img_bottom_photo.setImageResource(R.drawable.ic_photo_normal);
                img_bottom_resource.setImageResource(R.drawable.ic_resource_pressed);

                tv_bottom_weight.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_log.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_photo.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_calendar.setTextColor(Utils.getColor(this, R.color.color_9b9c9b));
                tv_bottom_resource.setTextColor(Utils.getColor(this, R.color.color_1296db));
                break;
        }


    }

    @Override
    public void onSlidMenuClick(int id) {
        switch (id) {
            case R.id.ll_home:
                hideOrShowSlidingMenu();
                break;
            case R.id.ll_weixin:
                Toast.makeText(this, "已复制到剪切板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_setting:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
