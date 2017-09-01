package com.xiaoxie.weightrecord.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

/**
 * desc:
 * Created by xiaoxie on 2017/8/29.
 */
public class Utils {
    /**
     * 获取颜色
     */
    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * 获取应用版本号
     */
    public static String getVersion(Context context) {
        int code = 0;
        String versionName = "";

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName + code;
    }

    /**
     * 设置语言
     */
    public static Context setCurrentLanguage(String language, Context context) {
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (language.equals("系统默认")) {
            language = Locale.getDefault().getLanguage();
        } else if (language.equals("简体中文")) {
            language = Locale.SIMPLIFIED_CHINESE.getLanguage();
        } else if (language.equals("繁体中文")) {
            language = Locale.TRADITIONAL_CHINESE.getLanguage();
        } else if (language.equals("English")) {
            language = Locale.ENGLISH.getLanguage();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        } else {
            return updateResourcesLegacy(context, language);
        }
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }


    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    /**
     * 获取屏幕指定大小位置的dialog
     *
     * @param dialog    返回的对话框实例
     * @param context   上下文
     * @param w         对话框宽比例
     * @param h         对话框高比例
     * @param locationX 在屏幕上的坐标x
     * @param locationY 在屏幕上的坐标y
     * @return
     */
    public static Dialog setCostumeDialogStyle(Dialog dialog, Context context, float w, float h, int locationX, int locationY, int grivaty1, int grivaty2) {
        int screenWidth;
        int screenHeight;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display d = ((Activity) context).getWindowManager().getDefaultDisplay();
            screenWidth = d.getWidth();
            screenHeight = d.getHeight();
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        }
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow == null) {
            return dialog;
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (screenWidth * w);
        lp.height = (int) (screenHeight * h);
        dialogWindow.setAttributes(lp);
        if (grivaty1 > 0 && grivaty2 > 0) {
            lp.gravity = grivaty1 | grivaty2;
        }
        if (grivaty1 > 0 && grivaty2 == 0) {
            lp.gravity = grivaty1;
        }
        if (locationX > 0 || locationY > 0) {
            lp.x = locationX;
            lp.y = locationY;
        }
        return dialog;
    }
}
