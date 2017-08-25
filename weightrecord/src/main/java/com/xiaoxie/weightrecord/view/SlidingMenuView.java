package com.xiaoxie.weightrecord.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.interfaces.SlidingMenuClickListener;

/**
 * desc:
 * Created by xiaoxie on 2017/8/24.
 */
public class SlidingMenuView extends LinearLayout implements View.OnClickListener {
    private LinearLayout ll_home;
    private LinearLayout ll_setting;
    private LinearLayout ll_weixin;
    private SlidingMenuClickListener listener;


    public SlidingMenuView(Context context) {
        super(context);
        initView(context);
    }

    public SlidingMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SlidingMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = View.inflate(context, R.layout.layout_slidingmenu, null);
        this.addView(view, lp);

        ll_home = view.findViewById(R.id.ll_home);
        ll_setting = view.findViewById(R.id.ll_setting);
        ll_weixin = view.findViewById(R.id.ll_weixin);
        ll_home.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_weixin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onSlidMenuClick(view.getId());
        }
    }

    public void setOnSildingMenuClickListener(SlidingMenuClickListener listener) {
        this.listener = listener;
    }
}
