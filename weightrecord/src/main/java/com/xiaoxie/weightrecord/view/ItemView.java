package com.xiaoxie.weightrecord.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

/**
 * desc:
 * Created by xiaoxie on 2017/9/14.
 */
public class ItemView extends LinearLayout {
    private Context context;
    private int i;
    private String title;
    private String value;

    public ItemView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ItemView(Context context, String title, String value) {
        super(context);
        this.context = context;
        this.title = title;
        this.value = value;

        initView();
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history_other_item_items, null);
        TextView tvTitle = view.findViewById(R.id.tv_history_other_title);
        TextView teContent = view.findViewById(R.id.tv_history_other_content);
        tvTitle.setText(title);
        teContent.setText(value);
        this.addView(view);
    }
}
