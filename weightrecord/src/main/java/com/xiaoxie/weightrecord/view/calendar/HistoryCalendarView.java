package com.xiaoxie.weightrecord.view.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xiaoxie.weightrecord.R;

import java.util.Calendar;

/**
 * 以日期的形式来展示历史数据
 * Created by gt on 2017/9/17.
 */

public class HistoryCalendarView extends LinearLayout implements OnDateSelectedListener {
    private Context context;
    private MaterialCalendarView mView;
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();

    public HistoryCalendarView(Context context) {
        super(context);
        initView(context);
    }

    public HistoryCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HistoryCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public HistoryCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history_calendar_view, null);
        mView = view.findViewById(R.id.materialCalendarView);

        mView.setOnDateChangedListener(this);
        mView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        mView.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        mView.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        mView.addDecorators(
                new DaySelectorDecorator(context),
                new HighlightWeekendsDecorator(),oneDayDecorator
        );
        mView.setArrowColor(R.color.yellow);

        this.addView(view);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        mView.invalidateDecorators();
    }
}
