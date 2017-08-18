package com.xiaoxie.weightrecord;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.xiaoxie.weightrecord.adapter.YearSelectAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

            wConfirm = layout.findViewById(R.id.bt_ok);
            wCancel = layout.findViewById(R.id.bt_cancel);
            bt_one = layout.findViewById(R.id.bt_one);
            bt_two = layout.findViewById(R.id.bt_two);
            bt_three = layout.findViewById(R.id.bt_three);
            bt_four = layout.findViewById(R.id.bt_four);
            bt_five = layout.findViewById(R.id.bt_five);
            bt_six = layout.findViewById(R.id.bt_six);
            bt_seven = layout.findViewById(R.id.bt_seven);
            bt_eight = layout.findViewById(R.id.bt_eight);
            bt_nine = layout.findViewById(R.id.bt_nine);
            bt_zero = layout.findViewById(R.id.bt_zero);
            bt_dot = layout.findViewById(R.id.bt_dot);
            tvweight = layout.findViewById(R.id.tv_weight);


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

    public static class HeightBuilder implements View.OnClickListener, NumberPicker.OnValueChangeListener, NumberPicker.Formatter, NumberPicker.OnScrollListener {
        private CustomDialog heightDialog;
        private Button height_Confirm;
        private Button height_Cancel;
        private HeightDialogOnClickListener listener;
        private String weight;
        private NumberPicker picker_ft;
        private NumberPicker picker_in;
        private int value_ft;
        private int value_in;

        public HeightBuilder(Context context) {
            initView(context);
        }

        public interface HeightDialogOnClickListener {
            void OnConfirmed(String height);

            void OnCanceled();
        }

        public void setOnHeightDialogOnclickListener(HeightDialogOnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
            if (numberPicker.getId() == R.id.picker_ft) {
                value_ft = newValue;
            } else if (numberPicker.getId() == R.id.picker_in) {
                value_in = newValue;
            }
        }

        @Override
        public String format(int i) {
            return null;
        }

        @Override
        public void onScrollStateChange(NumberPicker numberPicker, int i) {

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_height_confirm:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed(value_ft + "ft" + value_in + "in");
                    }
                    break;
                case R.id.bt_height_cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnCanceled();
                    }
                    break;
            }
        }

        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            heightDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_height, null);
            heightDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            height_Confirm = layout.findViewById(R.id.bt_height_confirm);
            height_Cancel = layout.findViewById(R.id.bt_height_cancel);
            picker_ft = layout.findViewById(R.id.picker_ft);
            picker_in = layout.findViewById(R.id.picker_in);
            picker_ft.setOnValueChangedListener(this);
            picker_in.setOnValueChangedListener(this);
            picker_ft.setMaxValue(9);
            picker_in.setMaxValue(9);

            height_Confirm.setOnClickListener(this);
            height_Cancel.setOnClickListener(this);

        }

        private void dismiss() {
            if (heightDialog != null && heightDialog.isShowing())
                heightDialog.dismiss();
        }

        public Dialog create() {
            return heightDialog;
        }

        public void show() {
            heightDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return heightDialog;
        }
    }

    public static class BirthdayBuilder implements View.OnClickListener, OnDateSelectedListener, OnMonthChangedListener, AdapterView.OnItemClickListener {
        private CustomDialog birthdayDialog;
        private Button bConfirm;
        private Button bCancel;
        private TextView tvYear;
        private TextView tvDate;
        private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
        private BirthdayDialogOnClickListener listener;
        private String date1;
        private String date2;
        private MaterialCalendarView materialCalendarView;
        private ListView mListView;
        private List<Integer> years = new ArrayList<>();
        private int year;

        public BirthdayBuilder(Context context) {
            initView(context);
        }

        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            date1 = getSelectedDatesString();
            tvDate.setText(date1);
            Log.d("weightrecode", "date1 = " + date1);
        }

        @Override
        public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
            date2 = FORMATTER.format(date.getDate());
            Log.d("weightrecode", "date2 = " + date2);
            tvDate.setText(date2);
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            year = years.get(i);
            Log.d("weightrecode","year = "+year);
            mListView.setVisibility(View.INVISIBLE);
            materialCalendarView.setVisibility(View.VISIBLE);
            tvYear.setText(year+"");
            Calendar calendar = Calendar.getInstance();
            int month = calendar.getTime().getMonth();
            int day = calendar.getTime().getDay();
            calendar.set(year, month, day);
            tvDate.setText(year + "年" + month + "月" + day + "日");
            materialCalendarView.setCurrentDate(calendar);
        }

        public interface BirthdayDialogOnClickListener {
            void OnConfirmed(String date);

            void OnCanceled();
        }

        public void setOnBirthdayDialogOnclickListener(BirthdayDialogOnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_calendarview_confirm:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed(date1);
                    }
                    break;
                case R.id.bt_calendarview_cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnCanceled();
                    }
                    break;
                case R.id.tvYear:
                    mListView.setVisibility(View.VISIBLE);
                    materialCalendarView.setVisibility(View.INVISIBLE);
                    break;
            }

        }

        private String getSelectedDatesString() {
            CalendarDay date = materialCalendarView.getSelectedDate();
            if (date == null) {
                return "No Selection";
            }
            return FORMATTER.format(date.getDate());
        }

        private void initData() {
            if (years == null) {
                return;
            }
            years.clear();
            for (int i = 0; i < 10000; i++) {
                years.add(i);
            }
        }

        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            birthdayDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_calendar_view, null);
            birthdayDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            materialCalendarView = layout.findViewById(R.id.calendarView);
            materialCalendarView.setSelectionColor(context.getResources().getColor(R.color.color_009688));
            bConfirm = layout.findViewById(R.id.bt_calendarview_confirm);
            bCancel = layout.findViewById(R.id.bt_calendarview_cancel);
            tvYear = layout.findViewById(R.id.tvYear);
            tvDate = layout.findViewById(R.id.tvDate);
            bConfirm.setOnClickListener(this);
            bCancel.setOnClickListener(this);
            tvYear.setOnClickListener(this);
            materialCalendarView.setOnDateChangedListener(this);
            materialCalendarView.setOnMonthChangedListener(this);
            initData();
            mListView = layout.findViewById(R.id.list_year);
            mListView.setOnItemClickListener(this);
            mListView.setAdapter(new YearSelectAdapter(context,years));
        }

        private void dismiss() {
            if (birthdayDialog != null && birthdayDialog.isShowing())
                birthdayDialog.dismiss();
        }

        public Dialog create() {
            return birthdayDialog;
        }

        public void show() {
            birthdayDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return birthdayDialog;
        }
    }

}
