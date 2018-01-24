package com.xiaoxie.weightrecord.application;

import android.app.Application;
import android.content.Context;

import com.xcb.network.okhttp.OkHttpUtils;
import com.xcb.network.okhttp.interceptor.LoggerInterceptor;
import com.xcb.network.okhttp.interceptor.RetryInterceptor;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

/**
 * desc:application类
 * Created by xiaoxie on 2017/8/11.
 */
public class BaseApplication extends Application {
    public static Context ApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContext = this.getApplicationContext();
        initData();
    }

    @Override
    protected void attachBaseContext(Context base) {
        Context context = Utils.setCurrentLanguage(SharePrefenceUtils.getString(base, SharePrefenceUtils.KEY_LANGUAGE, ""), base);
        if (context == null) {
            super.attachBaseContext(base);
        } else {
            super.attachBaseContext(context);
        }

    }

    /**
     * 初始化全局需要用到的配置
     */
    private void initData(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS)
                .writeTimeout(120,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new RetryInterceptor(3))
                .addInterceptor(new LoggerInterceptor("TAG"))
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
