package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.weather.bean.HourWeatherBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气预报
 * Created by xcb on 2018/2/11.
 */

public class WeatherForecast extends LinearLayout {
    private WeatherForecast weatherForecast;
    private Context context;
    private TextView tv_release_time;
    private TextView tv_release_institution;
    private TextView tv_tomorrow;
    private TextView tv_tomorrow_temperature_range;
    private TextView tv_future_2th_day;
    private TextView tv_future_2th_day_temperature_range;
    private TextView tv_future_3th_day;
    private TextView tv_future_3th_day_temperature_range;
    private TextView tv_future_4th_day;
    private TextView tv_future_4th_day_temperature_range;

    private ImageView iv_tomorrow_weather_icon;
    private ImageView iv_future_2th_day_weather_icon;
    private ImageView iv_future_3th_day_weather_icon;
    private ImageView iv_future_4th_day_weather_icon;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyAdapter adapter;

    public WeatherForecast(Context context) {
        super(context);
        initView(context);
    }

    public WeatherForecast(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.layout_weatherforecast, null);
        addView(v);
        tv_release_time = v.findViewById(R.id.tv_release_time);
        tv_release_institution = v.findViewById(R.id.tv_release_institution);
        tv_tomorrow = v.findViewById(R.id.tv_tomorrow);
        tv_tomorrow_temperature_range = v.findViewById(R.id.tv_tomorrow_temperature_range);
        tv_future_2th_day = v.findViewById(R.id.tv_future_2th_day);
        tv_future_2th_day_temperature_range = v.findViewById(R.id.tv_future_2th_day_temperature_range);
        tv_future_3th_day = v.findViewById(R.id.tv_future_3th_day);
        tv_future_3th_day_temperature_range = v.findViewById(R.id.tv_future_3th_day_temperature_range);
        tv_future_4th_day_temperature_range = v.findViewById(R.id.tv_future_4th_day_temperature_range);

        iv_tomorrow_weather_icon = v.findViewById(R.id.iv_tomorrow_weather_icon);
        iv_future_2th_day_weather_icon = v.findViewById(R.id.iv_future_2th_day_weather_icon);
        iv_future_3th_day_weather_icon = v.findViewById(R.id.iv_future_3th_day_weather_icon);
        iv_future_4th_day_weather_icon = v.findViewById(R.id.iv_future_4th_day_weather_icon);

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView = v.findViewById(R.id.hour_weather_list);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(context, getData());
        recyclerView.setAdapter(adapter);

    }

    private List<HourWeatherBean> getData() {
        List<HourWeatherBean> hourWeatherBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HourWeatherBean bean = new HourWeatherBean();
            if (i == 0) {
                bean.setTime("现在");
            } else {
                bean.setTime(i + "点");
            }
            bean.setTemperatureRange("19/8");
            hourWeatherBeanList.add(bean);
        }
        return hourWeatherBeanList;
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;
        private Context context;
        private List<HourWeatherBean> hourWeatherBeanList;

        public MyAdapter(Context context, List<HourWeatherBean> hourWeatherBeanList) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            this.hourWeatherBeanList = hourWeatherBeanList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.layout_hour_weather_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                HourWeatherBean bean = hourWeatherBeanList.get(position);
                if (bean != null) {
                    myViewHolder.time.setText(bean.getTime());
                    myViewHolder.iv_weather_icon.setImageResource(R.drawable.weather_sun);
                    myViewHolder.temperature.setText(bean.getTemperatureRange());
                }
            }
        }

        @Override
        public int getItemCount() {
            return hourWeatherBeanList.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private ImageView iv_weather_icon;
        private TextView temperature;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_hour_weather_title);
            iv_weather_icon = itemView.findViewById(R.id.iv_hour_weather_icon);
            temperature = itemView.findViewById(R.id.tv_hour_temperature);
        }

    }
}
