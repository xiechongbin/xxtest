package com.xiaoxie.weightrecord.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

/**
 * desc:fragment 工具类
 * Created by xiaoxie on 2017/8/23.
 */
public class FragmentUtils {
    /**
     * 添加 fragment
     * 如果使用的commit方法是在Activity的onSaveInstanceState()之后调用的，这样会出错，因为onSaveInstanceState
     * 方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存完状态后再给它添加Fragment就会出错。解决办法就
     * 是把commit()方法替换成commitAllowingStateLoss()就行了，其效果是一样的。
     *
     * @param activity        activity
     * @param containerViewID id
     * @param fragment        fragment
     * @param tag             标签
     */
    public static void addFragment(Activity activity, int containerViewID, Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.add(containerViewID, fragment, tag);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加fragment
     */
    public static void addFragment(Activity activity, int containerViewID, Fragment fragment, String tag, int enter, int exit) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(enter, exit);//效果
            fragmentTransaction.add(containerViewID, fragment, tag);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换fragment
     */
    public static void replaceFragment(Activity activity, int containerViewID, Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerViewID, fragment, tag);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换fragment
     */
    public static void replaceFragment(Activity activity, int containerViewID, Fragment fragment, String tag, int enter, int exit) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(enter, exit);
            fragmentTransaction.replace(containerViewID, fragment, tag);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示fragment
     */
    public static void showFragment(Activity activity, Fragment fragment) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            Log.d("fragmenttest", "weight: showFragment e = " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 显示fragment
     */
    public static void showFragment(Activity activity, Fragment fragment, int enter, int exit) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(enter, exit);
            fragmentTransaction.show(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏fragment
     */
    public static void hideFragment(Activity activity, Fragment fragment) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏fragment
     */
    public static void hideFragment(Activity activity, Fragment fragment, int enter, int exit) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(enter, exit);
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除fragment
     */
    public static void removeFragment(Activity activity, Fragment fragment) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除fragment
     */
    public static void removeFragment(Activity activity, Fragment fragment, int enter, int exit) {
        try {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(enter, exit);
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
