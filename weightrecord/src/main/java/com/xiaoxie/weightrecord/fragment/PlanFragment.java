package com.xiaoxie.weightrecord.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.MyViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * desc:
 * Created by xiaoxie on 2017/9/4.
 */
public class PlanFragment extends BaseFragment implements View.OnClickListener {
    private List<View> lists = new ArrayList<>();
    private Context context;
    private MyViewPager viewPager;
    private ImageView imageView_dot1;
    private ImageView imageView_dot2;
    private TextView tv_plan_year;
    private TextView tv_plan_date;

    private Bitmap bitmap_25;
    private Bitmap bitmap_30;

    private TextView tv_enable;
    private TextView tv_disable;
    private TextView tv_target_date;
    private TextView tv_target_weight;
    private ImageView iv_next;
    private LinearLayout ll_plan_reset_target_date;


    @Override
    protected int getLayoutId() {
        return R.layout.frag_plan_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.plan_viewpager);
        imageView_dot1 = view.findViewById(R.id.plan_first_page_dot);
        imageView_dot2 = view.findViewById(R.id.plan_second_page_dot);
    }

    @Override
    protected void initData() {
        this.context = getActivity();
        View fPage = LayoutInflater.from(context).inflate(R.layout.layout_plan_first_page, null);
        View sPage = LayoutInflater.from(context).inflate(R.layout.layout_plan_second_page, null);
        lists.add(fPage);
        lists.add(sPage);
        bitmap_25 = Utils.creatBitmap(25, 25);
        bitmap_30 = Utils.creatBitmap(30, 30);
        imageView_dot1.setImageBitmap(bitmap_30);
        imageView_dot2.setImageBitmap(bitmap_25);

        //第一个界面
        LinearLayout ll_plan_reset = fPage.findViewById(R.id.ll_plan_reset);
        tv_plan_year = fPage.findViewById(R.id.tv_plan_year);
        tv_plan_date = fPage.findViewById(R.id.tv_plan_date);
        ll_plan_reset.setOnClickListener(this);
        //第二个界面
        boolean isEnable = SharePrefenceUtils.getBoolean(context, SharePrefenceUtils.KEY_ENABLE_RESET_PLAN, false);
        iv_next = sPage.findViewById(R.id.iv_next);
        tv_enable = sPage.findViewById(R.id.plan_second_enable);
        tv_disable = sPage.findViewById(R.id.plan_second_disable);
        tv_target_date = sPage.findViewById(R.id.tv_target_date);
        tv_target_weight = sPage.findViewById(R.id.tv_plan_second_target_weight);
        ll_plan_reset_target_date = sPage.findViewById(R.id.ll_plan_second_target_date);
        tv_enable.setOnClickListener(this);
        tv_disable.setOnClickListener(this);
        tv_target_weight.setOnClickListener(this);
        ll_plan_reset_target_date.setOnClickListener(this);

        if (isEnable) {
            setEnabled();
        } else {
            setDisabled();
        }
        float tWeight = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_TARGET_WEIGHT, 0);
        tv_target_weight.setText(String.valueOf(tWeight));
        tv_target_date.setText(SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_TARGET_DATE, ""));
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return lists.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
                container.removeView(lists.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(lists.get(position));
                return lists.get(position);
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    imageView_dot1.setImageBitmap(bitmap_30);
                    imageView_dot2.setImageBitmap(bitmap_25);
                } else if (position == 1) {
                    imageView_dot1.setImageBitmap(bitmap_25);
                    imageView_dot2.setImageBitmap(bitmap_30);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_plan_reset:
                showDateResetDialog(false);
                break;
            case R.id.plan_second_enable:
                setEnabled();
                SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_ENABLE_RESET_PLAN, true);
                break;
            case R.id.plan_second_disable:
                setDisabled();
                SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_ENABLE_RESET_PLAN, false);
                break;
            case R.id.tv_plan_second_target_weight:
                showWeightDialog();
                break;
            case R.id.ll_plan_second_target_date:
                showDateResetDialog(true);
                break;

        }
    }

    private void setEnabled() {
        tv_enable.setBackgroundColor(Color.WHITE);
        tv_enable.setTextColor(Utils.getColor(context, R.color.color_theme_blue));
        tv_disable.setBackgroundColor(Color.TRANSPARENT);
        tv_disable.setTextColor(Color.WHITE);
        ll_plan_reset_target_date.setVisibility(View.VISIBLE);
        tv_target_weight.setVisibility(View.VISIBLE);
        iv_next.setVisibility(View.VISIBLE);
    }

    private void setDisabled() {
        tv_disable.setBackgroundColor(Color.WHITE);
        tv_disable.setTextColor(Utils.getColor(context, R.color.color_theme_blue));
        tv_enable.setBackgroundColor(Color.TRANSPARENT);
        tv_enable.setTextColor(Color.WHITE);

        ll_plan_reset_target_date.setVisibility(View.INVISIBLE);
        tv_target_weight.setVisibility(View.INVISIBLE);
        iv_next.setVisibility(View.INVISIBLE);
    }

    private void showDateResetDialog(final boolean target) {
        final CustomDialog.DateBuilder builder = new CustomDialog.DateBuilder(context);
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                int year = 0;
                int month = 0;
                int day = 0;
                int week = 0;
                HashMap<String, Integer> map = Utils.revertDate(str);
                if (map != null && map.size() > 0) {
                    if (map.containsKey("year")) {
                        year = map.get("year");
                    }
                    if (map.containsKey("month")) {
                        month = map.get("month");
                    }
                    if (map.containsKey("day")) {
                        day = map.get("day");
                    }
                    if (map.containsKey("week")) {
                        week = map.get("week");
                    }
                }
                if (target) {
                    tv_target_date.setText(str);
                    SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_TARGET_DATE, str);
                } else {
                    tv_plan_year.setText(String.valueOf(year));
                    tv_plan_date.setText(Utils.getWeek(context, week) + "," + month + "月" + day + "日");
                    SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_PLAN_START_TIME, str);
                }

            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 体重展示对话框
     */
    private void showWeightDialog() {
        final CustomDialog.WeightAndHeightBuilder builder = new CustomDialog.WeightAndHeightBuilder(context);
        builder.setOnWeightDialogOnclickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String weight) {
                if (TextUtils.isEmpty(weight))
                    return;
                tv_target_weight.setText(weight);
                SharePrefenceUtils.setFloat(context, SharePrefenceUtils.KEY_TARGET_WEIGHT, Float.valueOf(weight));
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }
}

