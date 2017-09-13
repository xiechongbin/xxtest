package com.xiaoxie.weightrecord.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.utils.CalculationUtils;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;

/**
 * desc:概述
 * Created by xiaoxie on 2017/9/1.
 */
public class OverViewFragment extends BaseFragment implements View.OnTouchListener {
    private LinearLayout overview_ll_weight;
    private LinearLayout overview_ll_height;
    private LinearLayout overview_ll_birthday;
    private LinearLayout overview_ll_sex;

    private LinearLayout overview_ll_healthy_range_weight;
    private LinearLayout overview_ll_healthy_range_bmi;
    private LinearLayout overview_ll_healthy_range_fat;
    private LinearLayout overview_ll_healthy_range_whr;

    private TextView tv_sex;
    private TextView tv_birthday;
    private TextView tv_height;
    private TextView tv_weight;

    private TextView tv_weight_healthy_range;
    private TextView tv_bmi_healthy_range;
    private TextView tv_fat_healthy_range;
    private TextView tv_whr_healthy_range;

    private TextView tv_current_weight;
    private TextView tv_current_bmi;
    private TextView tv_current_fat;
    private TextView tv_current_whr;

    private Context context;
    private float minbmi = 18.5f;
    private float maxbmi = 24.0f;
    private int minFat = 5;
    private int maxFat = 25;

    @Override

    protected int getLayoutId() {
        return R.layout.frag_overview_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        overview_ll_weight = view.findViewById(R.id.overview_ll_weight);
        overview_ll_height = view.findViewById(R.id.overview_ll_height);
        overview_ll_birthday = view.findViewById(R.id.overview_ll_birthday);
        overview_ll_sex = view.findViewById(R.id.overview_ll_sex);

        overview_ll_healthy_range_weight = view.findViewById(R.id.overview_ll_healthy_range_weight);
        overview_ll_healthy_range_bmi = view.findViewById(R.id.overview_ll_healthy_range_bmi);
        overview_ll_healthy_range_fat = view.findViewById(R.id.overview_ll_healthy_range_fat);
        overview_ll_healthy_range_whr = view.findViewById(R.id.overview_ll_healthy_range_whr);

        tv_sex = view.findViewById(R.id.overview_tv_sex);
        tv_birthday = view.findViewById(R.id.overview_tv_birthday);
        tv_height = view.findViewById(R.id.overview_tv_height);
        tv_weight = view.findViewById(R.id.overview_tv_weight);

        tv_weight_healthy_range = view.findViewById(R.id.overview_tv_weight_healthy_range);
        tv_bmi_healthy_range = view.findViewById(R.id.overview_tv_bmi_healthy_range);
        tv_fat_healthy_range = view.findViewById(R.id.overview_tv_fat_healthy_range);
        tv_whr_healthy_range = view.findViewById(R.id.overview_tv_whr_healthy_range);

        tv_current_weight = view.findViewById(R.id.overview_tv_current_weight);
        tv_current_bmi = view.findViewById(R.id.overview_tv_current_bmi);
        tv_current_fat = view.findViewById(R.id.overview_tv_current_fat);
        tv_current_whr = view.findViewById(R.id.overview_tv_current_whr);

        overview_ll_weight.setOnTouchListener(this);
        overview_ll_height.setOnTouchListener(this);
        overview_ll_birthday.setOnTouchListener(this);
        overview_ll_sex.setOnTouchListener(this);

        overview_ll_healthy_range_weight.setOnTouchListener(this);
        overview_ll_healthy_range_bmi.setOnTouchListener(this);
        overview_ll_healthy_range_fat.setOnTouchListener(this);
        overview_ll_healthy_range_whr.setOnTouchListener(this);


    }

    @Override
    protected void initData() {
        context = getActivity();
        tv_sex.setText(SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_SEX, ""));
        tv_birthday.setText(SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_BIRTHDAY, ""));
        String currentHeight = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_HEIGHT, 0) + "";
        tv_height.setText(currentHeight);
        String currentWeight = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_WEIGHT, 0) + "";
        tv_weight.setText(currentWeight);
        float[] floats = CalculationUtils.calculateNormalWeight(context);
        tv_weight_healthy_range.setText(floats[0] + "-" + floats[2]);
        tv_current_weight.setText(floats[3] + "");
        tv_bmi_healthy_range.setText(minbmi + "-" + maxbmi);
        tv_current_bmi.setText((float) (Math.round(SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_BMI, 0) * 100) / 100) + "");
        tv_fat_healthy_range.setText(minFat + "%" + "-" + maxFat + "%");
        tv_current_fat.setText((float) (Math.round(CalculationUtils.calculateBodyFat(context) * 100)) / 100 + "%");
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            switch (view.getId()) {
                case R.id.overview_ll_weight:
                    overview_ll_weight.setBackgroundColor(Utils.getColor(getActivity(), R.color.color_theme_blue));
                    break;
                case R.id.overview_ll_height:
                    overview_ll_height.setBackgroundColor(Utils.getColor(getActivity(), R.color.color_theme_blue));
                    break;
                case R.id.overview_ll_sex:
                    overview_ll_sex.setBackgroundColor(Utils.getColor(getActivity(), R.color.color_theme_blue));
                    break;
                case R.id.overview_ll_birthday:
                    overview_ll_birthday.setBackgroundColor(Utils.getColor(getActivity(), R.color.color_theme_blue));
                    break;
            }

        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d("aaaddd", "move");
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            switch (view.getId()) {
                case R.id.overview_ll_weight:
                    overview_ll_weight.setBackgroundResource(R.drawable.overview_layer_list);
                    break;
                case R.id.overview_ll_height:
                    overview_ll_height.setBackgroundResource(R.drawable.overview_layer_list);
                    showHeightDialog();
                    break;
                case R.id.overview_ll_sex:
                    overview_ll_sex.setBackgroundResource(R.drawable.overview_layer_list);
                    showSexDialog();
                    break;
                case R.id.overview_ll_birthday:
                    overview_ll_birthday.setBackgroundResource(R.drawable.overview_layer_list);
                    showBirthdayDialog();
                    break;
            }

        }
        return true;
    }

    /**
     * 性别展示对话框
     */
    private void showSexDialog() {
        final CustomDialog.SexBuilder builder = new CustomDialog.SexBuilder(context);
        builder.setOnSexDialogOnclickListener(new CustomDialog.SexBuilder.SexDialogOnClickListener() {
            @Override
            public void OnConfirmed(String sex) {
                if (TextUtils.isEmpty(sex))
                    return;
                tv_sex.setText(sex);
                SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_SEX, sex);//重新保存sex
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }

    /**
     * 身高展示对话框
     */
    private void showHeightDialog() {
        final CustomDialog.WeightAndHeightBuilder builder = new CustomDialog.WeightAndHeightBuilder(context, "cm");
        builder.setOnWeightDialogOnclickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String height) {
                if (TextUtils.isEmpty(height))
                    return;
                tv_height.setText(height);
                SharePrefenceUtils.setFloat(context, SharePrefenceUtils.KEY_INITIAL_HEIGHT, Float.valueOf(height));//重新保存身高
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }

    /**
     * 生日展示对话框
     */
    private void showBirthdayDialog() {
        final CustomDialog.BirthdayBuilder builder = new CustomDialog.BirthdayBuilder(context);
        builder.setOnBirthdayDialogOnclickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String date) {
                if (TextUtils.isEmpty(date))
                    return;
                tv_birthday.setText(date);

                SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_BIRTHDAY, date);//重新保存生日
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }
}
