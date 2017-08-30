package com.xiaoxie.weightrecord.application;

import android.app.Application;
import android.content.Context;

import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;

/**
 * desc:application类
 * Created by xiaoxie on 2017/8/11.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

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
}
