package com.xiaoxie.weightrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.view.GestureLockView;

import org.apache.http.conn.scheme.HostNameResolver;

import java.lang.ref.SoftReference;

public class LockPatternActivity extends AppCompatActivity implements GestureLockView.GestureLockCallback {
    private GestureLockView lockView;
    private String savedPassword;
    private String firstPassword;
    private String secondPassword;
    private TextView tvTitle;
    private int count = 0;
    private Context context;
    private boolean isFromSetting;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern);
        init();
    }

    private void init() {
        context = this;
        lockView = (GestureLockView) findViewById(R.id.id_gestureLockView);
        tvTitle = (TextView) findViewById(R.id.tv_lockView_title);
        savedPassword = SharePrefenceUtils.getString(this, SharePrefenceUtils.KEY_PASSWORD, "");
        isFromSetting = getIntent().getBooleanExtra("isFromSetting", false);
        lockView.setCallback(this);
        handler = new MyHandler(this);
    }

    @Override
    public void onFinish(String pwdString, GestureLockView.Result result) {
        if (isFromSetting) {
            count++;
            if (count == 1) {
                firstPassword = pwdString;
                tvTitle.setText(R.string.draw_again);
                Log.d("password", "firstPassword = " + firstPassword);
            }
            if (count == 2) {
                secondPassword = pwdString;
                Log.d("password", "secondPassword = " + secondPassword);
                count = 0;
            }
            if (!TextUtils.isEmpty(firstPassword) && !TextUtils.isEmpty(secondPassword)) {
                if (firstPassword.equals(secondPassword)) {
                    //两次设置的密码相匹配 保存密码
                    Log.d("password", "67hang");
                    SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_PASSWORD, secondPassword);
                    SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_HAS_PASSWORD, true);
                    tvTitle.setText(R.string.draw_success);
                    handler.sendEmptyMessageDelayed(1112, 2000);
                } else {
                    Log.d("password", "71hang");
                    result.setRight(false);
                    tvTitle.setText(R.string.two_times_error);
                    count = 0;//重新设置密码
                    handler.sendEmptyMessageDelayed(1111, 2000);
                }
            }
        } else {
            if (TextUtils.isEmpty(savedPassword)) {
                return;
            }
            boolean isRight = savedPassword.equals(pwdString);
            result.setRight(isRight);
            Toast.makeText(LockPatternActivity.this, isRight ? "密码正确" : "密码错误", Toast.LENGTH_LONG).show();
            if (isRight) {
                Intent intent = new Intent();
                intent.putExtra("unLockSuccess",false);
                intent.setClass(LockPatternActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }


    }

    private static class MyHandler extends Handler {
        private LockPatternActivity activity;
        SoftReference<LockPatternActivity> reference;

        public MyHandler(LockPatternActivity activity) {
            reference = new SoftReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activity = reference.get();
            if (msg.what == 1111) {
                activity.tvTitle.setText(R.string.draw);
            } else if (msg.what == 1112) {
                activity.finish();
            }
        }
    }
}
