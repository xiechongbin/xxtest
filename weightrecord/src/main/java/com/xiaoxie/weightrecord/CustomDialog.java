package com.xiaoxie.weightrecord;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.xiaoxie.weightrecord.adapter.YearSelectAdapter;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        private CustomDialog sexDialog;
        private Button mBtnConfirm;
        private Button mBtnCancel;
        private RadioGroup group;
        private RadioButton radioButton_man;
        private RadioButton radioButton_woman;
        private SexDialogOnClickListener listener;
        private Context context;
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

        private void initView(Context context) {
            this.context = context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            sexDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_sex, null);
            sexDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
                sex = context.getText(R.string.man).toString();
            } else if (i == R.id.woman) {
                sex = context.getText(R.string.woman).toString();
            } else {
                sex = "";
            }
        }

        private void dismiss() {
            if (sexDialog != null && sexDialog.isShowing())
                sexDialog.dismiss();
        }

        public Dialog create() {
            return sexDialog;
        }

        public void show() {
            sexDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return sexDialog;
        }
    }

    public static class WeightAndHeightBuilder implements View.OnClickListener {
        private CustomDialog weightAndHeightDialog;
        private Context context;
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
        private ImageButton bt_delete;
        private TextView tvWeight;
        private TextView tvUnit;
        private String value = "";
        private DialogClickListener listener;
        private boolean isHeight = false;

        public WeightAndHeightBuilder(Context context) {
            initView(context);
        }

        public WeightAndHeightBuilder(Context context, boolean isHeight) {
            this.isHeight = isHeight;
            initView(context);
        }

        public void setOnWeightDialogOnclickListener(DialogClickListener listener) {
            this.listener = listener;
        }

        private void initView(Context context) {
            this.context = context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            weightAndHeightDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_keyobard, null);
            weightAndHeightDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
            tvWeight = layout.findViewById(R.id.tv_weight);
            tvUnit = layout.findViewById(R.id.tv_unit);//单位
            bt_delete = layout.findViewById(R.id.bt_delete);

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
            bt_delete.setOnClickListener(this);

            if (isHeight) {
                tvUnit.setText("CM");
            }

        }

        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(value) && value.length() > 5 && (view.getId() != R.id.bt_delete)) {
                Toast.makeText(context, context.getText(R.string.outrange), Toast.LENGTH_SHORT).show();
                return;
            }
            switch (view.getId()) {
                case R.id.bt_ok:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed(value);
                    }
                    break;
                case R.id.bt_cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnCanceled();
                    }
                    break;
                case R.id.bt_one:
                    value = value + "1";
                    break;
                case R.id.bt_two:
                    value = value + "2";
                    break;
                case R.id.bt_three:
                    value = value + "3";
                    break;
                case R.id.bt_four:
                    value = value + "4";
                    break;
                case R.id.bt_five:
                    value = value + "5";
                    break;
                case R.id.bt_six:
                    value = value + "6";
                    break;
                case R.id.bt_seven:
                    value = value + "7";
                    break;
                case R.id.bt_eight:
                    value = value + "8";
                    break;
                case R.id.bt_nine:
                    value = value + "9";
                    break;
                case R.id.bt_zero:
                    value = value + "0";
                    break;
                case R.id.bt_dot:
                    value = value + ".";
                    break;
                case R.id.bt_delete:
                    if (value.length() > 0) {
                        value = value.substring(0, value.length() - 1);
                    }
                    break;
            }
            tvWeight.setText(value);
        }

        private void dismiss() {
            if (weightAndHeightDialog != null && weightAndHeightDialog.isShowing())
                weightAndHeightDialog.dismiss();
        }

        public Dialog create() {
            return weightAndHeightDialog;
        }

        public void show() {
            weightAndHeightDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return weightAndHeightDialog;
        }
    }

    public static class BirthdayBuilder implements View.OnClickListener, OnDateSelectedListener, OnMonthChangedListener, AdapterView.OnItemClickListener {
        private CustomDialog birthdayDialog;
        private Button bConfirm;
        private Button bCancel;
        private TextView tvYear;
        private TextView tvDate;
        private MaterialCalendarView materialCalendarView;
        private ListView mListView;
        private LinearLayout layout_year_select;

        private DialogClickListener listener;
        private YearSelectAdapter adapter;
        private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
        private int year;
        private String date1;
        private String date2;

        public BirthdayBuilder(Context context) {
            initView(context);
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
            layout_year_select = layout.findViewById(R.id.ll_year_select);
            mListView = layout.findViewById(R.id.list_year);
            bConfirm.setOnClickListener(this);
            bCancel.setOnClickListener(this);
            tvYear.setOnClickListener(this);
            materialCalendarView.setOnDateChangedListener(this);
            materialCalendarView.setOnMonthChangedListener(this);
            mListView.setOnItemClickListener(this);
            adapter = new YearSelectAdapter(context);
            mListView.setAdapter(adapter);

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            tvYear.setText(currentYear+"");
            mListView.setSelection(currentYear);//设置当前位置
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
            year = (Integer) adapter.getItem(i);
            Log.d("weightrecode", "year = " + year);
            layout_year_select.setVisibility(View.GONE);
            materialCalendarView.setVisibility(View.VISIBLE);
            tvYear.setText(year + "");
            Calendar calendar = Calendar.getInstance();
            int month = calendar.getTime().getMonth();
            int day = calendar.getTime().getDay();
            calendar.set(year, month, day);
            tvDate.setText(year + "年" + month + "月" + day + "日");
            materialCalendarView.setCurrentDate(calendar);
        }

        public void setOnBirthdayDialogOnclickListener(DialogClickListener listener) {
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
                    layout_year_select.setVisibility(View.VISIBLE);
                    materialCalendarView.setVisibility(View.GONE);
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
