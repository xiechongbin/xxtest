package com.xiaoxie.weightrecord.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.view.ItemView;

public class TestActivity extends AppCompatActivity {
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        layout = (LinearLayout) findViewById(R.id.ll_test);
        layout.setOrientation(LinearLayout.VERTICAL);

        // 将TextView 加入到LinearLayout 中
        TextView tv = new TextView(this);
        tv.setText("Hello World");
        layout.addView(tv);

        // 将Button 1 加入到LinearLayout 中
        Button b1 = new Button(this);
        b1.setText("取消");
        layout.addView(b1);

        // 将Button 2 加入到LinearLayout 中
        Button b2 = new Button(this);
        b2.setText("确定");
        layout.addView(b2);
        for (int i = 0; i < 5; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           // View view = new ItemView(this, i);
           // layout.addView(view,layoutParams);
        }

    }
}
