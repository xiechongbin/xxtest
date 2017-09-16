package com.xiaoxie.weightrecord.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.bean.BodyData;
import com.xiaoxie.weightrecord.utils.CalculationUtils;
import com.xiaoxie.weightrecord.utils.LogUtils;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;

/**
 * desc:
 * Created by xiaoxie on 2017/9/13.
 */
public class HistoryView extends LinearLayout {
    private Context context;
    private TextView tv_his_weekday;
    private TextView tv_his_date;
    private RelativeLayout rl_history_items;
    private BodyData bodyData;
    private View weightItemView;
    private LinearLayout otherItemView;
    private int screenWidth;
    private int screenHeight;

    public HistoryView(Context context, BodyData bodyData) {
        super(context);
        this.context = context;
        this.bodyData = bodyData;
        initView();
    }

    public HistoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public HistoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public HistoryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }

    private void initView() {
        int[] ints = Utils.getScreenWidthAndHeight(context);
        screenWidth = ints[0];
        screenHeight = ints[1];

        View view = LayoutInflater.from(context).inflate(R.layout.layout_history, this);
        tv_his_weekday = view.findViewById(R.id.tv_his_weekday);
        tv_his_date = view.findViewById(R.id.tv_his_date);
        rl_history_items = view.findViewById(R.id.rl_history_items);

        String date = bodyData.getDate();
        tv_his_weekday.setText(Utils.getWeekDay(context, date));
        tv_his_date.setText(date);
        addWeightItem();
        addOtherDataItem();
    }

    /**
     * 添加体重的view
     */
    private void addWeightItem() {
        weightItemView = LayoutInflater.from(context).inflate(R.layout.layout_history_weight_item, null);
        TextView tv_his_weight = weightItemView.findViewById(R.id.tv_his_weight);
        TextView tv_his_unit = weightItemView.findViewById(R.id.tv_his_unit);
        TextView tv_his_bmi = weightItemView.findViewById(R.id.tv_his_bmi);
        TextView tv_his_conclusion = weightItemView.findViewById(R.id.tv_his_conclusion);
        ImageView img_weight_trend = weightItemView.findViewById(R.id.img_weight_trend);
        TextView tv_weight_trend = weightItemView.findViewById(R.id.tv_weight_trend);
        String unit = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_WEIGHT_UNIT, "kg");
        tv_his_weight.setText(String.valueOf(bodyData.getAverageWeight()));
        tv_his_unit.setText(unit);
        tv_his_bmi.setText(String.valueOf(bodyData.getBmi()));
        tv_his_conclusion.setText(CalculationUtils.getBmiConclusion(bodyData.getBmi()));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.width = (screenWidth / 3) * 2;
        layoutParams.height = screenHeight / 9;
        weightItemView.setLayoutParams(layoutParams);
        weightItemView.setId(View.generateViewId());
        rl_history_items.addView(weightItemView);
    }

    /**
     * 添加其他数据的view
     */
    private void addOtherDataItem() {
        otherItemView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_history_other_item, null);
        otherItemView.setOrientation(LinearLayout.VERTICAL);
        LogUtils.printBodyData("bodydata", "addOtherDataItem", bodyData);

        float amWeight = bodyData.getAmWeight();
        if (amWeight > 0) {
            View amView = new ItemView(context, context.getString(R.string.label_weight_morning), String.valueOf(amWeight));
            otherItemView.addView(amView);
        }
        float pmWeight = bodyData.getPmWeight();
        if (pmWeight > 0) {
            View pmView = new ItemView(context, context.getString(R.string.label_weight_noon), String.valueOf(pmWeight));
            otherItemView.addView(pmView);
        }
        float nightWeight = bodyData.getNightWeight();
        if (nightWeight > 0) {
            View nmView = new ItemView(context, context.getString(R.string.label_weight_night), String.valueOf(nightWeight));
            otherItemView.addView(nmView);
        }
        float averageWeight = bodyData.getAverageWeight();
        if (averageWeight > 0) {
            View avView = new ItemView(context, context.getString(R.string.label_weight_avg), String.valueOf(averageWeight));
            otherItemView.addView(avView);
        }
        float bodyFat = bodyData.getBodyFat();
        if (bodyFat > 0) {
            View fView = new ItemView(context, context.getString(R.string.label_fat), String.valueOf(bodyFat));
            otherItemView.addView(fView);
        }
        float internalOrgansFat = bodyData.getInternalOrgansFat();
        if (internalOrgansFat > 0) {
            View iView = new ItemView(context, context.getString(R.string.label_visceral_fat), String.valueOf(internalOrgansFat));
            otherItemView.addView(iView);
        }

        float muscle = bodyData.getMuscle();
        if (muscle > 0) {
            View muscleView = new ItemView(context, context.getString(R.string.label_muscle), String.valueOf(muscle));
            otherItemView.addView(muscleView);
        }
        float bone = bodyData.getBone();
        if (bone > 0) {
            View boneView = new ItemView(context, context.getString(R.string.label_bones), String.valueOf(bone));
            otherItemView.addView(boneView);
        }
        float bodyMoisture = bodyData.getBodyMoisture();
        if (bodyMoisture > 0) {
            View bmView = new ItemView(context, context.getString(R.string.label_body_water), String.valueOf(bodyMoisture));
            otherItemView.addView(bmView);
        }
        float heartRate = bodyData.getHeartRate();
        if (heartRate > 0) {
            View heartView = new ItemView(context, context.getString(R.string.label_heart_rate), String.valueOf(heartRate));
            otherItemView.addView(heartView);
        }
        float bmr = bodyData.getBmr();
        if (bmr > 0) {
            View bmrView = new ItemView(context, context.getString(R.string.label_bmr), String.valueOf(bmr));
            otherItemView.addView(bmrView);
        }
        float bmi = bodyData.getBmi();
        if (bmi > 0) {
            View bmiView = new ItemView(context, context.getString(R.string.label_bmi), String.valueOf(bmi));
            otherItemView.addView(bmiView);
        }
        float biceps = bodyData.getBiceps();
        if (biceps > 0) {
            View bicepsView = new ItemView(context, context.getString(R.string.label_bicep), String.valueOf(biceps));
            otherItemView.addView(bicepsView);
        }
        float neck = bodyData.getNeck();//颈部
        if (neck > 0) {
            View neckView = new ItemView(context, context.getString(R.string.label_neck), String.valueOf(neck));
            otherItemView.addView(neckView);
        }
        float waist = bodyData.getWaist();//腰
        if (waist > 0) {
            View waistView = new ItemView(context, context.getString(R.string.label_waist), String.valueOf(waist));
            otherItemView.addView(waistView);
        }
        float wrist = bodyData.getWrist();//腕部
        if (wrist > 0) {
            View wristView = new ItemView(context, context.getString(R.string.label_wrist), String.valueOf(wrist));
            otherItemView.addView(wristView);
        }
        float forearm = bodyData.getForearm();//前臂
        if (forearm > 0) {
            View forearmView = new ItemView(context, context.getString(R.string.label_forearm), String.valueOf(forearm));
            otherItemView.addView(forearmView);
        }
        float buttocks = bodyData.getButtocks();//臀部
        if (buttocks > 0) {
            View buttocksView = new ItemView(context, context.getString(R.string.label_hips), String.valueOf(buttocks));
            otherItemView.addView(buttocksView);
        }
        float bust = bodyData.getBust();//胸围
        if (bust > 0) {
            View bustView = new ItemView(context, context.getString(R.string.label_bust), String.valueOf(bust));
            otherItemView.addView(bustView);
        }
        float thigh = bodyData.getThigh();//大腿
        if (thigh > 0) {
            View thighView = new ItemView(context, context.getString(R.string.label_thighs), String.valueOf(thigh));
            otherItemView.addView(thighView);
        }
        float abdomen = bodyData.getAbdomen();//腹部
        if (abdomen > 0) {
            View abdomenView = new ItemView(context, context.getString(R.string.label_belly), String.valueOf(abdomen));
            otherItemView.addView(abdomenView);
        }
        float chest = bodyData.getChest();//胸部
        if (chest > 0) {
            View chestView = new ItemView(context, context.getString(R.string.label_chest), String.valueOf(chest));
            otherItemView.addView(chestView);
        }
        int diet = bodyData.getDiet();//饮食 星级
        if (diet > 0) {
            View dietView = new ItemView(context, context.getString(R.string.label_diet), String.valueOf(diet));
            otherItemView.addView(dietView);
        }
        int activity = bodyData.getActivity();//活动 星级
        if (chest > 0) {
            View activityView = new ItemView(context, context.getString(R.string.label_activity), String.valueOf(activity));
            otherItemView.addView(activityView);
        }
        String annotate = bodyData.getAnnotate();//注释
        if (!TextUtils.isEmpty(annotate)) {
            View annotateView = new ItemView(context, context.getString(R.string.label_comment), String.valueOf(annotate));
            otherItemView.addView(annotateView);
        }


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.BELOW, weightItemView.getId());
        layoutParams.topMargin = 10;
        layoutParams.width = (screenWidth / 3) * 2;
        otherItemView.setLayoutParams(layoutParams);
        rl_history_items.addView(otherItemView);
    }
}
