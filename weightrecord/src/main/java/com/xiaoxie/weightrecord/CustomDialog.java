package com.xiaoxie.weightrecord;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * desc:对话框工具类
 * Created by xiaoxie on 2017/8/15.
 */
public class CustomDialog extends Dialog {


    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class SexBuilder implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
        private CustomDialog SexDialog;
        private RadioButton radioButton_man;
        private RadioButton radioButton_woman;
        private Button mBtnConfirm;
        private Button mBtnCancel;
        private SexDialogOnClickListener listener;
        private RadioGroup group;
        private String sex;

        public SexBuilder(Context context) {
            initView(context);
        }


        public interface SexDialogOnClickListener {
            void OnConfirmed(String sex);

            void OnCanceled();
        }

        public void setOnSexDialogOnclickListener(SexDialogOnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.confirm:
                    Log.d("weightrecord", "sex = " + sex);
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed(sex);
                    }
                    break;
                case R.id.cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnCanceled();
                    }
                    break;
                case R.id.man:
                    break;
                case R.id.woman:
                    break;
            }
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.man) {
                sex = "男";
            } else if (i == R.id.woman) {
                sex = "女";
            } else {
                sex = "";
            }
        }


        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            SexDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_sex, null);
            SexDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            group = (RadioGroup) layout.findViewById(R.id.radiogroup);
            radioButton_man = (RadioButton) layout.findViewById(R.id.man);
            radioButton_woman = (RadioButton) layout.findViewById(R.id.woman);
            mBtnConfirm = (Button) layout.findViewById(R.id.confirm);
            mBtnCancel = (Button) layout.findViewById(R.id.cancel);
            radioButton_man.setOnClickListener(this);
            radioButton_woman.setOnClickListener(this);
            mBtnConfirm.setOnClickListener(this);
            mBtnCancel.setOnClickListener(this);
            group.setOnCheckedChangeListener(this);
        }

        private void dismiss() {
            if (SexDialog != null && SexDialog.isShowing())
                SexDialog.dismiss();
        }

        public Dialog create() {
            return SexDialog;
        }

        public void show() {
            SexDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return SexDialog;
        }
    }

    public static class WeightBuilder implements View.OnClickListener {
        private CustomDialog weightDialog;
        private Button wConfirm;
        private Button wCancel;
        private Button bt_one;
        private Button bt_two;
        private Button bt_three;
        private Button bt_four;
        private Button bt_five;
        private Button bt_six;
        private Button bt_seven;
        private Button bt_eight;
        private Button bt_nine;
        private Button bt_zero;
        private Button bt_dot;
        private TextView tvweight;
        private WeightDialogOnClickListener listener;
        private String weight;
        private String unit = "ST";

        public WeightBuilder(Context context) {
            initView(context);
        }


        public interface WeightDialogOnClickListener {
            void OnConfirmed(String weight);

            void OnCanceled();
        }

        public void setOnWeightDialogOnclickListener(WeightDialogOnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_ok:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed(weight);
                    }
                    break;
                case R.id.bt_cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnCanceled();
                    }
                    break;
                case R.id.bt_one:
                    weight = weight + "1";
                    break;
                case R.id.bt_two:
                    weight = weight + "2";
                    break;
                case R.id.bt_three:
                    weight = weight + "3";
                    break;
                case R.id.bt_four:
                    weight = weight + "4";
                    break;
                case R.id.bt_five:
                    weight = weight + "5";
                    break;
                case R.id.bt_six:
                    weight = weight + "6";
                    break;
                case R.id.bt_seven:
                    weight = weight + "7";
                    break;
                case R.id.bt_eight:
                    weight = weight + "8";
                    break;
                case R.id.bt_nine:
                    weight = weight + "9";
                    break;
                case R.id.bt_zero:
                    weight = weight + "0";
                    break;
                case R.id.bt_dot:
                    weight = weight + ".";
                    break;
            }
            tvweight.setText(weight);
        }

        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            weightDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_keyobard, null);
            weightDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            wConfirm = (Button) layout.findViewById(R.id.bt_ok);
            wCancel = (Button) layout.findViewById(R.id.bt_cancel);
            bt_one = (Button) layout.findViewById(R.id.bt_one);
            bt_two = (Button) layout.findViewById(R.id.bt_two);
            bt_three = (Button) layout.findViewById(R.id.bt_three);
            bt_four = (Button) layout.findViewById(R.id.bt_four);
            bt_five = (Button) layout.findViewById(R.id.bt_five);
            bt_six = (Button) layout.findViewById(R.id.bt_six);
            bt_seven = (Button) layout.findViewById(R.id.bt_seven);
            bt_eight = (Button) layout.findViewById(R.id.bt_eight);
            bt_nine = (Button) layout.findViewById(R.id.bt_nine);
            bt_zero = (Button) layout.findViewById(R.id.bt_zero);
            bt_dot = (Button) layout.findViewById(R.id.bt_dot);
            tvweight = (TextView) layout.findViewById(R.id.tv_weight);


            wConfirm.setOnClickListener(this);
            wCancel.setOnClickListener(this);
            bt_one.setOnClickListener(this);
            bt_two.setOnClickListener(this);
            bt_three.setOnClickListener(this);
            bt_four.setOnClickListener(this);
            bt_five.setOnClickListener(this);
            bt_six.setOnClickListener(this);
            bt_seven.setOnClickListener(this);
            bt_eight.setOnClickListener(this);
            bt_nine.setOnClickListener(this);
            bt_zero.setOnClickListener(this);
            bt_dot.setOnClickListener(this);

        }

        private void dismiss() {
            if (weightDialog != null && weightDialog.isShowing())
                weightDialog.dismiss();
        }

        public Dialog create() {
            return weightDialog;
        }

        public void show() {
            weightDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return weightDialog;
        }
    }


}
