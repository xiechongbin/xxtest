package com.xiaoxie.weightrecord.weather.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.weather.utils.Constant;

/**
 * desc:对话框工具类
 * Created by xiaoxie on 2017/8/15.
 */
public class WeatherDialog extends Dialog {


    public WeatherDialog(Context context) {
        super(context);
    }

    public WeatherDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WeatherDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class TemperatureUnitBuilder implements View.OnClickListener {
        private WeatherDialog temperatureUnitDialog;

        private TextView mBtnCancel;
        private RelativeLayout tv_auto;
        private RelativeLayout tv_unit_c;
        private RelativeLayout tv_unit_f;
        private ImageView iv_temperature_c_selected;
        private ImageView iv_temperature_auto_selected;
        private ImageView iv_temperature_f_selected;
        private TemperatureUnitDialogOnClickListener listener;
        private Context context;
        private Drawable drawableNormal;
        private Drawable drawableSelect;
        private String unit;

        private SharedPreferences sharedPreferences;

        public TemperatureUnitBuilder(Context context) {
            sharedPreferences = context.getSharedPreferences(Constant.CONFIG_TEMPERATURE_UNIT, Context.MODE_PRIVATE);
            initView(context);
        }

        public interface TemperatureUnitDialogOnClickListener {
            void onClicked(String unit);
        }

        public void setOnTemperatureUnitDialogOnClickListener(TemperatureUnitDialogOnClickListener listener) {
            this.listener = listener;
        }

        private void initView(Context context) {
            this.context = context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            temperatureUnitDialog = new WeatherDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_weather_unit, null);
            temperatureUnitDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            tv_auto = layout.findViewById(R.id.temperature_unit_item_auto);
            tv_unit_c = layout.findViewById(R.id.temperature_unit_item_c);
            tv_unit_f = layout.findViewById(R.id.temperature_unit_item_f);

            iv_temperature_c_selected = layout.findViewById(R.id.iv_temperature_c_selected);
            iv_temperature_f_selected = layout.findViewById(R.id.iv_temperature_f_selected);
            iv_temperature_auto_selected = layout.findViewById(R.id.iv_temperature_auto_selected);

            String unit = sharedPreferences.getString(Constant.TEMPERATURE_UNIT, context.getString(R.string.weather_temperature_unit_auto));
            drawableNormal = context.getDrawable(R.drawable.shape_temperature_unit_normal);
            drawableNormal.setBounds(0, 0, 10, 10);

            drawableSelect = context.getDrawable(R.drawable.shape_temperature_unit_selected);
            drawableSelect.setBounds(0, 0, 10, 10);
            if (unit.equals(context.getString(R.string.weather_temperature_unit_auto))) {
                iv_temperature_auto_selected.setImageDrawable(drawableSelect);
            } else if (unit.equals(context.getString(R.string.weather_temperature_unit_c))) {
                iv_temperature_c_selected.setImageDrawable(drawableSelect);
            } else if (unit.equals(context.getString(R.string.weather_temperature_unit_f))) {
                iv_temperature_f_selected.setImageDrawable(drawableSelect);
            }

            mBtnCancel = layout.findViewById(R.id.tv_cancel);
            tv_auto.setOnClickListener(this);
            tv_unit_c.setOnClickListener(this);
            tv_unit_f.setOnClickListener(this);
            mBtnCancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.temperature_unit_item_auto:
                    sharedPreferences.edit().putString(Constant.TEMPERATURE_UNIT, context.getString(R.string.weather_temperature_unit_auto)).apply();
                    iv_temperature_auto_selected.setImageDrawable(drawableSelect);
                    iv_temperature_c_selected.setImageDrawable(drawableNormal);
                    iv_temperature_f_selected.setImageDrawable(drawableNormal);
                    unit = context.getString(R.string.weather_temperature_unit_auto);
                    dismiss();
                    break;
                case R.id.temperature_unit_item_c:
                    sharedPreferences.edit().putString(Constant.TEMPERATURE_UNIT, context.getString(R.string.weather_temperature_unit_c)).apply();
                    iv_temperature_auto_selected.setImageDrawable(drawableNormal);
                    iv_temperature_c_selected.setImageDrawable(drawableSelect);
                    iv_temperature_f_selected.setImageDrawable(drawableNormal);
                    unit = context.getString(R.string.weather_temperature_unit_c);
                    dismiss();
                    break;
                case R.id.temperature_unit_item_f:
                    sharedPreferences.edit().putString(Constant.TEMPERATURE_UNIT, context.getString(R.string.weather_temperature_unit_f)).apply();
                    iv_temperature_auto_selected.setImageDrawable(drawableNormal);
                    iv_temperature_c_selected.setImageDrawable(drawableNormal);
                    iv_temperature_f_selected.setImageDrawable(drawableSelect);
                    unit = context.getString(R.string.weather_temperature_unit_f);
                    dismiss();
                    break;
                case R.id.tv_cancel:
                    dismiss();
                    break;
            }
            listener.onClicked(unit);
        }


        private void dismiss() {
            if (temperatureUnitDialog != null && temperatureUnitDialog.isShowing())
                temperatureUnitDialog.dismiss();
        }

        public Dialog create() {
            temperatureUnitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            return temperatureUnitDialog;
        }

        public void show() {
            temperatureUnitDialog.show();
        }

        public WeatherDialog getAlertDialog() {
            return temperatureUnitDialog;
        }
    }

    public static class UpdateIntervalBuilder implements View.OnClickListener {
        private WeatherDialog updateIntervalDialog;

        private TextView mBtnCancel;
        private RelativeLayout rl_auto_update_interval_item_1;
        private RelativeLayout rl_auto_update_interval_item_2;
        private RelativeLayout rl_auto_update_interval_item_6;
        private RelativeLayout rl_auto_update_interval_item_12;
        private RelativeLayout rl_auto_update_interval_item_24;

        private ImageView iv_auto_update_1;
        private ImageView iv_auto_update_2;
        private ImageView iv_auto_update_6;
        private ImageView iv_auto_update_12;
        private ImageView iv_auto_update_24;

        private UpdateIntervalBuilderClickListener listener;
        private Context context;
        private Drawable drawableNormal;
        private Drawable drawableSelect;
        private String interval;

        private SharedPreferences sharedPreferences;

        public UpdateIntervalBuilder(Context context) {
            sharedPreferences = context.getSharedPreferences(Constant.CONFIG_TEMPERATURE_UNIT, Context.MODE_PRIVATE);
            initView(context);
        }

        public interface UpdateIntervalBuilderClickListener {
            void onClicked(String interval);
        }

        public void setOnUpdateIntervalClickListener(UpdateIntervalBuilderClickListener listener) {
            this.listener = listener;
        }

        private void initView(Context context) {
            this.context = context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            updateIntervalDialog = new WeatherDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_update_interval, null);
            updateIntervalDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            rl_auto_update_interval_item_1 = layout.findViewById(R.id.auto_update_interval_item_1);
            rl_auto_update_interval_item_2 = layout.findViewById(R.id.auto_update_interval_item_2);
            rl_auto_update_interval_item_6 = layout.findViewById(R.id.auto_update_interval_item_6);
            rl_auto_update_interval_item_12 = layout.findViewById(R.id.auto_update_interval_item_12);
            rl_auto_update_interval_item_24 = layout.findViewById(R.id.auto_update_interval_item_24);

            iv_auto_update_1 = layout.findViewById(R.id.iv_auto_update_1);
            iv_auto_update_2 = layout.findViewById(R.id.iv_auto_update_2);
            iv_auto_update_6 = layout.findViewById(R.id.iv_auto_update_6);
            iv_auto_update_12 = layout.findViewById(R.id.iv_auto_update_12);
            iv_auto_update_24 = layout.findViewById(R.id.iv_auto_update_24);

            interval = sharedPreferences.getString(Constant.UPDATE_INTERVAL, context.getString(R.string.one_hour));
            drawableNormal = context.getDrawable(R.drawable.shape_temperature_unit_normal);
            drawableNormal.setBounds(0, 0, 10, 10);

            drawableSelect = context.getDrawable(R.drawable.shape_temperature_unit_selected);
            drawableSelect.setBounds(0, 0, 10, 10);
            if (interval.equals(context.getString(R.string.one_hour))) {
                iv_auto_update_1.setImageDrawable(drawableSelect);
            } else if (interval.equals(context.getString(R.string.two_hour))) {
                iv_auto_update_2.setImageDrawable(drawableSelect);
            } else if (interval.equals(context.getString(R.string.six_hour))) {
                iv_auto_update_6.setImageDrawable(drawableSelect);
            } else if (interval.equals(context.getString(R.string.twelve_hour))) {
                iv_auto_update_12.setImageDrawable(drawableSelect);
            } else if (interval.equals(context.getString(R.string.twenty_four_hour))) {
                iv_auto_update_24.setImageDrawable(drawableSelect);
            }

            mBtnCancel = layout.findViewById(R.id.tv_cancel);
            rl_auto_update_interval_item_1.setOnClickListener(this);
            rl_auto_update_interval_item_2.setOnClickListener(this);
            rl_auto_update_interval_item_6.setOnClickListener(this);
            rl_auto_update_interval_item_12.setOnClickListener(this);
            rl_auto_update_interval_item_24.setOnClickListener(this);
            mBtnCancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.auto_update_interval_item_1:
                    interval = context.getString(R.string.one_hour);
                    sharedPreferences.edit().putString(Constant.UPDATE_INTERVAL, interval).apply();
                    iv_auto_update_1.setImageDrawable(drawableSelect);
                    iv_auto_update_2.setImageDrawable(drawableNormal);
                    iv_auto_update_6.setImageDrawable(drawableNormal);
                    iv_auto_update_12.setImageDrawable(drawableNormal);
                    iv_auto_update_24.setImageDrawable(drawableNormal);
                    dismiss();
                    break;
                case R.id.auto_update_interval_item_2:
                    interval = context.getString(R.string.two_hour);
                    sharedPreferences.edit().putString(Constant.UPDATE_INTERVAL, interval).apply();
                    iv_auto_update_1.setImageDrawable(drawableNormal);
                    iv_auto_update_2.setImageDrawable(drawableSelect);
                    iv_auto_update_6.setImageDrawable(drawableNormal);
                    iv_auto_update_12.setImageDrawable(drawableNormal);
                    iv_auto_update_24.setImageDrawable(drawableNormal);
                    dismiss();
                    break;
                case R.id.auto_update_interval_item_6:
                    interval = context.getString(R.string.six_hour);
                    sharedPreferences.edit().putString(Constant.UPDATE_INTERVAL, interval).apply();
                    iv_auto_update_1.setImageDrawable(drawableNormal);
                    iv_auto_update_2.setImageDrawable(drawableNormal);
                    iv_auto_update_6.setImageDrawable(drawableSelect);
                    iv_auto_update_12.setImageDrawable(drawableNormal);
                    iv_auto_update_24.setImageDrawable(drawableNormal);
                    dismiss();
                    break;
                case R.id.auto_update_interval_item_12:
                    interval = context.getString(R.string.twelve_hour);
                    sharedPreferences.edit().putString(Constant.UPDATE_INTERVAL, interval).apply();
                    iv_auto_update_1.setImageDrawable(drawableNormal);
                    iv_auto_update_2.setImageDrawable(drawableNormal);
                    iv_auto_update_6.setImageDrawable(drawableNormal);
                    iv_auto_update_12.setImageDrawable(drawableSelect);
                    iv_auto_update_24.setImageDrawable(drawableNormal);
                    dismiss();
                    break;
                case R.id.auto_update_interval_item_24:
                    interval = context.getString(R.string.twenty_four_hour);
                    sharedPreferences.edit().putString(Constant.UPDATE_INTERVAL, interval).apply();
                    iv_auto_update_1.setImageDrawable(drawableNormal);
                    iv_auto_update_2.setImageDrawable(drawableNormal);
                    iv_auto_update_6.setImageDrawable(drawableNormal);
                    iv_auto_update_12.setImageDrawable(drawableNormal);
                    iv_auto_update_24.setImageDrawable(drawableSelect);
                    dismiss();
                    break;
                case R.id.tv_cancel:
                    dismiss();
                    break;
            }
            listener.onClicked(interval);
        }


        private void dismiss() {
            if (updateIntervalDialog != null && updateIntervalDialog.isShowing())
                updateIntervalDialog.dismiss();
        }

        public Dialog create() {
            updateIntervalDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            return updateIntervalDialog;
        }

        public void show() {
            updateIntervalDialog.show();
        }

        public WeatherDialog getAlertDialog() {
            return updateIntervalDialog;
        }
    }
}
