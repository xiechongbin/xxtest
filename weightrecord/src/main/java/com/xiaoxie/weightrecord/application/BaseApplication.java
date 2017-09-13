package com.xiaoxie.weightrecord.application;

import android.app.Application;
import android.content.Context;

import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

    }
}
