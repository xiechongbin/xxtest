package com.xiaoxie.weightrecord.weather;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;
import com.xiaoxie.weightrecord.weather.dialog.WeatherDialog;
import com.xiaoxie.weightrecord.weather.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.weather.utils.Constant;

public class WeatherSettingActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView setting_recycleView;
    private SettingAdapter adapter;
    private ActionBar actionBar;
    private View dividing_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_setting);
        initActionBar();
        initView();
    }

    private void initView() {
        setting_recycleView = (RecyclerView) findViewById(R.id.setting_recycleView);
        dividing_line = findViewById(R.id.dividing_line);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = Utils.dip2px(this, 15);
        layoutParams.rightMargin = Utils.dip2px(this, 15);
        setting_recycleView.setLayoutParams(layoutParams);
        setting_recycleView.setLayoutManager(layoutManager);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(this, 2));
        layoutParams1.topMargin = getActionBarHeight() + Utils.dip2px(this, 20);
        dividing_line.setLayoutParams(layoutParams1);

        int color = getResources().getColor(R.color.color_divider_line, null);
        setting_recycleView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, color));
        adapter = new SettingAdapter(this);
        adapter.setOnItemClickListener(this);
        setting_recycleView.setAdapter(adapter);
    }

    private int getActionBarHeight() {
        TypedArray array = this.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int height = array.getDimensionPixelOffset(0, 0);
        array.recycle();
        return height;
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        Drawable drawable = getDrawable(android.R.color.transparent);
        actionBar.setDisplayHomeAsUpEnabled(true); //显示返回的箭头，并可通过onOptionsItemSelected()进行监听，其资源ID为android.R.id.home。
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(drawable);
        actionBar.setTitle(R.string.setting);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                showTemperatureDialog();
                break;
            case 1:
                adapter.notifyDataSetChanged();
                break;
            case 2:
                adapter.notifyDataSetChanged();
                break;
            case 3:
                showUpdateIntervalDialog();
                break;
        }
    }

    private void showTemperatureDialog() {
        WeatherDialog.TemperatureUnitBuilder builder = new WeatherDialog.TemperatureUnitBuilder(this);
        final Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
          /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.35); // 高度设置为屏幕的0.35
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        window.setAttributes(p);


        builder.setOnTemperatureUnitDialogOnClickListener(new WeatherDialog.TemperatureUnitBuilder.TemperatureUnitDialogOnClickListener() {
            @Override
            public void onClicked(String unit) {
                adapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    private void showUpdateIntervalDialog() {
        WeatherDialog.UpdateIntervalBuilder builder = new WeatherDialog.UpdateIntervalBuilder(this);
        final Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
          /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.55); // 高度设置为屏幕的0.55
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        window.setAttributes(p);

        builder.setOnUpdateIntervalClickListener(new WeatherDialog.UpdateIntervalBuilder.UpdateIntervalBuilderClickListener() {
            @Override
            public void onClicked(String unit) {
                adapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    private class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private int count = 4;
        private int ViewTypeText = 1;
        private int ViewTypeSwitch = 2;
        private Context context;
        private OnItemClickListener listener;
        private String unit;
        private SharedPreferences sharedPreferences;
        private boolean weather_sound;
        private boolean auto_update;

        SettingAdapter(Context context) {
            this.context = context;
            sharedPreferences = context.getSharedPreferences(Constant.CONFIG_TEMPERATURE_UNIT, MODE_PRIVATE);
            weather_sound = sharedPreferences.getBoolean(Constant.WEATHER_SOUND, false);
            auto_update = sharedPreferences.getBoolean(Constant.AUTO_UPDATE, false);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ViewTypeText) {
                return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.layout_weather_setting_item1, parent, false));
            } else {
                return new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.layout_weather_setting_item2, parent, false));
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof ViewHolder1) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            if (position == 3 && !auto_update) {
                                return;
                            }
                            listener.onItemClick(position);
                        }
                    }
                });
                ViewHolder1 holder1 = (ViewHolder1) holder;
                if (position == 0) {
                    holder1.tv_title.setText(R.string.weather_temperature_unit);
                    holder1.tv_value.setText(sharedPreferences.getString(Constant.TEMPERATURE_UNIT, getResources().getString(R.string.weather_temperature_unit_auto)));
                } else if (position == 3) {
                    if (!sharedPreferences.getBoolean(Constant.AUTO_UPDATE, false)) {
                        holder1.tv_title.setTextColor(context.getResources().getColor(R.color.color_beb8c6, null));
                        holder1.tv_value.setTextColor(context.getResources().getColor(R.color.color_beb8c6, null));

                    } else {
                        holder1.tv_title.setTextColor(Color.WHITE);
                    }
                    holder1.tv_title.setText(R.string.weather_update_every_time);
                    holder1.tv_value.setText(R.string.weather_update_every_time_one_hour);
                }
            } else if (holder instanceof ViewHolder2) {
                ViewHolder2 holder2 = (ViewHolder2) holder;
                if (position == 1) {
                    holder2.tv_title.setText(R.string.weather_sound);
                    holder2.aSwitch.setChecked(sharedPreferences.getBoolean(Constant.WEATHER_SOUND, false));
                } else if (position == 2) {
                    holder2.tv_title.setText(R.string.weather_auto_update);
                    holder2.aSwitch.setChecked(sharedPreferences.getBoolean(Constant.AUTO_UPDATE, false));
                }
                holder2.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (position == 1) {
                            weather_sound = isChecked;
                            sharedPreferences.edit().putBoolean(Constant.WEATHER_SOUND, isChecked).apply();
                        } else if (position == 2) {
                            auto_update = isChecked;
                            sharedPreferences.edit().putBoolean(Constant.AUTO_UPDATE, isChecked).apply();
                        }
                        if (listener != null) {
                            listener.onItemClick(position);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == 3) {
                return ViewTypeText;
            } else {
                return ViewTypeSwitch;
            }
        }


        @Override
        public int getItemCount() {
            return count;
        }
    }

    private class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_value;

        ViewHolder1(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.setting_title1);
            tv_value = itemView.findViewById(R.id.setting_value1);
        }
    }

    private class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private Switch aSwitch;

        ViewHolder2(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.setting_title2);
            aSwitch = itemView.findViewById(R.id.switch_bar);
        }
    }

}
