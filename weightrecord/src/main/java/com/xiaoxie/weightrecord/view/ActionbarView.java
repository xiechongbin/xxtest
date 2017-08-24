package com.xiaoxie.weightrecord.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.interfaces.ActionBarClickListener;

/**
 * desc:自定义导航栏view
 * Created by xiaoxie on 2017/8/23.
 */
public class ActionbarView extends LinearLayout implements View.OnClickListener {
    private ImageView imageView_menu;
    private ImageView imageView_notification;
    private ImageView imageView_cloud;
    private ImageView imageView_more;

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
        imageView_more = v.findViewById(R.id.img_more);

        imageView_menu.setOnClickListener(this);
        imageView_notification.setOnClickListener(this);
        imageView_cloud.setOnClickListener(this);
        imageView_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(clickListener != null ){
            clickListener.onActionBarClick(view.getId());
        }
    }

    public void setOnActionBarClickListener(ActionBarClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
