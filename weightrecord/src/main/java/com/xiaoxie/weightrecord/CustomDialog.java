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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.xiaoxie.weightrecord.activity.LockPatternActivity;
import com.xiaoxie.weightrecord.adapter.CommonAdapter;
import com.xiaoxie.weightrecord.adapter.YearSelectAdapter;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;

import java.io.FileReader;
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
            if (!TextUtils.isEmpty(value) && value.length() > 5 && (view.getId() != R.id.bt_delete) && (view.getId() != R.id.bt_cancel)) {
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
            tvYear.setText(currentYear + "");
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

    public static class CommonBuilder implements View.OnClickListener, AdapterView.OnItemClickListener, CommonAdapter.radioButtonClickCallback {
        private CustomDialog commonDialog;
        private TextView lConfirm;
        private TextView lCancel;
        private TextView tv_title;
        private ListView mListView;

        private DialogClickListener listener;
        private CommonAdapter adapter;
        private int position;
        private String[] strings;
        private String str;

        public CommonBuilder(Context context) {
            initView(context);
        }

        public CommonBuilder(Context context, String[] str) {
            this.strings = str;
            initView(context);
        }

        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            commonDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_common, null);
            commonDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            lConfirm = layout.findViewById(R.id.tv_language_confirm);
            lCancel = layout.findViewById(R.id.tv_language_cancel);
            tv_title = layout.findViewById(R.id.tv_common_title);
            mListView = layout.findViewById(R.id.common_list);
            lConfirm.setOnClickListener(this);
            lCancel.setOnClickListener(this);
            mListView.setOnItemClickListener(this);
            mListView.setDividerHeight(0);
            adapter = new CommonAdapter(context, strings);
            mListView.setAdapter(adapter);
            adapter.setCallback(this);
        }

        @Override
        public void onRadioButtonClick(String str) {
            this.str = str;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            this.position = i;
        }

        public void setOnLanguageDialogClickListener(DialogClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_language_confirm:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed(str);
                    }
                    break;
                case R.id.tv_language_cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnCanceled();
                    }
                    break;
            }

        }

        private void dismiss() {
            if (commonDialog != null && commonDialog.isShowing())
                commonDialog.dismiss();
        }

        public CommonBuilder setTitle(String title) {
            if (!TextUtils.isEmpty(title)) {
                tv_title.setText(title);
            }
            return this;
        }

        public Dialog create() {
            return commonDialog;
        }

        public void show() {
            commonDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return commonDialog;
        }
    }

    public static class UnitBuilder implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
        private CustomDialog unitDialog;
        private TextView uConfirm;
        private TextView uCancel;
        private RadioGroup group_weight_unit;
        private RadioGroup group_height_unit;

        private String unit_weight;
        private String unit_height;

        private DialogClickListener listener;

        public UnitBuilder(Context context) {
            initView(context);
        }

        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            unitDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_unit, null);
            unitDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            uConfirm = layout.findViewById(R.id.tv_unit_confirm);
            uCancel = layout.findViewById(R.id.tv_unit_cancel);

            group_weight_unit = layout.findViewById(R.id.group_weight_unit);
            group_height_unit = layout.findViewById(R.id.group_height_unit);
            uConfirm.setOnClickListener(this);
            uCancel.setOnClickListener(this);

            group_height_unit.setOnCheckedChangeListener(this);
            group_weight_unit.setOnCheckedChangeListener(this);
        }


        public void setOnDialogClickListener(DialogClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
            if (radioGroup.getId() == R.id.group_height_unit) {
                if (id == R.id.rb_in) {
                    unit_height = "in";
                } else if (id == R.id.rb_cm) {
                    unit_height = "cm";
                }
            } else if (radioGroup.getId() == R.id.group_weight_unit) {
                if (id == R.id.rb_kg) {
                    unit_weight = "kg";
                } else if (id == R.id.rb_lb) {
                    unit_weight = "lb";
                } else if (id == R.id.rb_st) {
                    unit_weight = "st";
                }
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_unit_confirm:
                    if (listener != null) {
                        if (TextUtils.isEmpty(unit_weight) || TextUtils.isEmpty(unit_height)) {
                            listener.OnConfirmed("null");
                        } else {
                            dismiss();
                            listener.OnConfirmed(unit_weight + "," + unit_height);
                        }

                    }
                    break;
                case R.id.tv_unit_cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnCanceled();
                    }
                    break;
            }

        }

        private void dismiss() {
            if (unitDialog != null && unitDialog.isShowing())
                unitDialog.dismiss();
        }


        public Dialog create() {
            return unitDialog;
        }

        public void show() {
            unitDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return unitDialog;
        }
    }

    public static class InputTypeBuilder implements View.OnClickListener {
        private CustomDialog inputTypeDialog;
        private TextView tv_roller_input;
        private TextView tv_direct_input;

        private DialogClickListener listener;
        private String content1, content2;

        public InputTypeBuilder(Context context) {
            initView(context);
        }

        public InputTypeBuilder(Context context, String content1, String content2) {
            this.content1 = content1;
            this.content2 = content2;
            initView(context);
        }

        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inputTypeDialog = new CustomDialog(context, R.style.myDialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_input_methed, null);
            inputTypeDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tv_roller_input = layout.findViewById(R.id.tv_roller_input);
            tv_direct_input = layout.findViewById(R.id.tv_direct_input);
            if (!TextUtils.isEmpty(content1)) {
                tv_roller_input.setText(content1);
            }
            if (!TextUtils.isEmpty(content2)) {
                tv_direct_input.setText(content2);
            }
            tv_direct_input.setOnClickListener(this);
            tv_roller_input.setOnClickListener(this);
        }

        public void setOnDialogClickListener(DialogClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_roller_input:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed(tv_roller_input.getText().toString());
                    }
                    break;
                case R.id.tv_direct_input:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed((tv_direct_input.getText().toString()));
                    }
                    break;
            }

        }

        private void dismiss() {
            if (inputTypeDialog != null && inputTypeDialog.isShowing())
                inputTypeDialog.dismiss();
        }


        public Dialog create() {
            return inputTypeDialog;
        }

        public void show() {
            inputTypeDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return inputTypeDialog;
        }
    }

    public static class TimeBuilder implements View.OnClickListener {
        private CustomDialog timeDialog;
        private TextView tvTimeConfirm;
        private TextView tvTimeCancel;
        private TimePicker timePicker;
        private int hour, minute;
        private boolean changed = false;

        private DialogClickListener listener;

        public TimeBuilder(Context context) {
            initView(context);
        }

        private void initView(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            timeDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_time_setting, null);
            timeDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tvTimeConfirm = layout.findViewById(R.id.tv_time_confirm);
            tvTimeCancel = layout.findViewById(R.id.tv_time_cancel);
            timePicker = layout.findViewById(R.id.timePicker);
            timePicker.setOnClickListener(this);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                    hour = i;
                    minute = i1;
                    changed = true;
                }
            });

            tvTimeConfirm.setOnClickListener(this);
            tvTimeCancel.setOnClickListener(this);
        }

        public void setOnDialogClickListener(DialogClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_time_confirm:
                    if (listener != null) {
                        dismiss();
                        String time;
                        if (!changed) {
                            hour = timePicker.getHour();
                            minute = timePicker.getMinute();
                        }
                        if (hour > 12) {
                            time = "下午" + (hour - 12) + ":" + minute;
                        } else if (hour == 0) {
                            time = "下午" + hour + ":" + minute;
                        } else if (hour > 0 && hour < 12) {
                            time = "上午" + hour + ":" + minute;
                        } else {
                            time = "中午" + hour + ":" + minute;
                        }
                        listener.OnConfirmed(time);
                    }
                    break;
                case R.id.tv_time_cancel:
                    if (listener != null) {
                        dismiss();
                        listener.OnConfirmed("");
                    }
                    break;
            }

        }

        private void dismiss() {
            if (timeDialog != null && timeDialog.isShowing())
                timeDialog.dismiss();
        }


        public Dialog create() {
            return timeDialog;
        }

        public void show() {
            timeDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return timeDialog;
        }
    }

    public static class WeekBuilder implements View.OnClickListener {
        private CustomDialog weekDialog;
        private TextView tvWeekConfirm;
        private TextView tvWeekCancel;

        private Context context;

        private CheckBox checkBox1;
        private CheckBox checkBox2;
        private CheckBox checkBox3;
        private CheckBox checkBox4;
        private CheckBox checkBox5;
        private CheckBox checkBox6;
        private CheckBox checkBox7;
        private String sum;

        private DialogClickListener listener;

        public WeekBuilder(Context context) {
            initView(context);
        }

        private void initView(Context context) {
            this.context = context;
            sum = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_REMINDER_WHICH_DAY, "");
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            weekDialog = new CustomDialog(context, R.style.Theme_AppCompat_Dialog);
            View layout = mInflater.inflate(R.layout.layout_dialog_week_choose, null);
            weekDialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tvWeekConfirm = layout.findViewById(R.id.tv_week_confirm);
            tvWeekCancel = layout.findViewById(R.id.tv_week_cancel);

            checkBox1 = layout.findViewById(R.id.checkbox1);
            checkBox2 = layout.findViewById(R.id.checkbox2);
            checkBox3 = layout.findViewById(R.id.checkbox3);
            checkBox4 = layout.findViewById(R.id.checkbox4);
            checkBox5 = layout.findViewById(R.id.checkbox5);
            checkBox6 = layout.findViewById(R.id.checkbox6);
            checkBox7 = layout.findViewById(R.id.checkbox7);

            checkBox1.setOnClickListener(this);
            checkBox2.setOnClickListener(this);
            checkBox3.setOnClickListener(this);
            checkBox4.setOnClickListener(this);
            checkBox5.setOnClickListener(this);
            checkBox6.setOnClickListener(this);
            checkBox7.setOnClickListener(this);

            if (sum != null) {
                if (sum.contains("一")) {
                    checkBox1.setChecked(true);
                }
                if (sum.contains("二")) {
                    checkBox2.setChecked(true);
                }
                if (sum.contains("三")) {
                    checkBox3.setChecked(true);
                }
                if (sum.contains("四")) {
                    checkBox4.setChecked(true);
                }
                if (sum.contains("五")) {
                    checkBox5.setChecked(true);
                }
                if (sum.contains("六")) {
                    checkBox6.setChecked(true);
                }
                if (sum.contains("日")) {
                    checkBox7.setChecked(true);
                }
            }

            tvWeekConfirm.setOnClickListener(this);
            tvWeekCancel.setOnClickListener(this);
        }

        public void setOnDialogClickListener(DialogClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_week_confirm:
                    if (listener != null) {
                        dismiss();
                        StringBuilder builder = new StringBuilder();
                        if (checkBox7.isChecked()) {
                            builder.append(context.getText(R.string.sunday));
                        }
                        if (checkBox1.isChecked()) {
                            builder.append(context.getText(R.string.monday));
                        }
                        if (checkBox2.isChecked()) {
                            builder.append(context.getText(R.string.tuesday));
                        }
                        if (checkBox3.isChecked()) {
                            builder.append(context.getText(R.string.wednesday));
                        }
                        if (checkBox4.isChecked()) {
                            builder.append(context.getText(R.string.thursday));
                        }
                        if (checkBox5.isChecked()) {
                            builder.append(context.getText(R.string.friday));
                        }
                        if (checkBox6.isChecked()) {
                            builder.append(context.getText(R.string.saturday));
                        }
                        listener.OnConfirmed(builder.toString());
                    }
                    break;
                case R.id.tv_week_cancel:
                    if (listener != null) {
                        dismiss();
                    }
                    break;
                case R.id.checkbox1:
                    checkBox1.setChecked(checkBox1.isChecked());
                    break;
                case R.id.checkbox2:
                    checkBox2.setChecked(checkBox2.isChecked());
                    break;
                case R.id.checkbox3:
                    checkBox3.setChecked(checkBox3.isChecked());
                    break;
                case R.id.checkbox4:
                    checkBox4.setChecked(checkBox4.isChecked());
                    break;
                case R.id.checkbox5:
                    checkBox5.setChecked(checkBox5.isChecked());
                    break;
                case R.id.checkbox6:
                    checkBox6.setChecked(checkBox6.isChecked());

                    break;
                case R.id.checkbox7:
                    checkBox7.setChecked(checkBox7.isChecked());
                    break;


            }

        }

        private void dismiss() {
            if (weekDialog != null && weekDialog.isShowing())
                weekDialog.dismiss();
        }


        public Dialog create() {
            return weekDialog;
        }

        public void show() {
            weekDialog.show();
        }

        public CustomDialog getAlertDialog() {
            return weekDialog;
        }
    }
}
