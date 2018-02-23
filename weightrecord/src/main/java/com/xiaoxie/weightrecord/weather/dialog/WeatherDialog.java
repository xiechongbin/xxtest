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


}
