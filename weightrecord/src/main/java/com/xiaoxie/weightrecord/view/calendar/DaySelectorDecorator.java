package com.xiaoxie.weightrecord.view.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.xiaoxie.weightrecord.R;

/**
 * 对选中日期增加定制图层
 * Created by gt on 2017/9/17.
 */

public class DaySelectorDecorator implements DayViewDecorator {
    private Drawable drawable;

    public DaySelectorDecorator(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.my_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        //view.setBackgroundDrawable(drawable);
    }
}
