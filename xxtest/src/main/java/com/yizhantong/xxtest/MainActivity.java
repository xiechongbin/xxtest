package com.yizhantong.xxtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yizhantong.xxtest.activity.HandlerTestActivity;
import com.yizhantong.xxtest.activity.RecycleViewTest2Activity;
import com.yizhantong.xxtest.activity.RecycleViewTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_handlertest;
    private Button bt_RecycleView;
    private Button bt_RecycleView1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListener();
    }

    private void findViews() {
        bt_handlertest = (Button) findViewById(R.id.bt_handlertest);
        bt_RecycleView = (Button) findViewById(R.id.bt_RecycleView);
        bt_RecycleView1 = (Button) findViewById(R.id.bt_RecycleView1);
    }

    private void setListener() {
        bt_handlertest.setOnClickListener(this);
        bt_RecycleView.setOnClickListener(this);
        bt_RecycleView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_handlertest://handler 测试
                intent = new Intent(MainActivity.this, HandlerTestActivity.class);
                break;
            case R.id.bt_RecycleView://RecycleView测试
                intent = new Intent(MainActivity.this, RecycleViewTestActivity.class);
                break;
            case R.id.bt_RecycleView1://RecycleView测试
                intent = new Intent(MainActivity.this, RecycleViewTest2Activity.class);
                break;
        }
        startActivity(intent);
    }
}
