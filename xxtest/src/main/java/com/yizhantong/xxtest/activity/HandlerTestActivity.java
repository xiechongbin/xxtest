package com.yizhantong.xxtest.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yizhantong.xxtest.R;

public class HandlerTestActivity extends AppCompatActivity {

    private final Handler handler = new Handler(){//此写法易导致内存泄露
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        handler.sendMessageDelayed(Message.obtain(),10000);
        //finish();
    }
}
