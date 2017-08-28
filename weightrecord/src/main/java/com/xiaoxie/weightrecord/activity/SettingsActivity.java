package com.xiaoxie.weightrecord.activity;

import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.fragment.ReminderSettingFragment;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.utils.FragmentUtils;
import com.xiaoxie.weightrecord.adapter.RecycleViewAdapter;
import com.xiaoxie.weightrecord.fragment.BackupFragment;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.view.ActionbarView;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;

import java.util.Date;

public class SettingsActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView recycleView;
    private RecycleViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private BackupFragment backupFragment;
    private ReminderSettingFragment reminderSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initRecycleView();
        setCustomActionBar();
    }

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView = (RecyclerView) findViewById(R.id.setting_recycleView);
        recycleView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getColor(R.color.color_f2f2f2)));
        recycleView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(this);
        adapter.setOnItemClickListener(this);
        recycleView.setAdapter(adapter);
    }

    /**
     * 自定义标题栏
     */
    private void setCustomActionBar() {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        ActionbarView view = new ActionbarView(this);
        view.setWitchToShow(SettingsActivity.class.getSimpleName());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(view, layoutParams);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onItemClick(int position, int id) {
        Toast.makeText(this, "position = " + position + ">>id = " + id, Toast.LENGTH_SHORT).show();
        if (position == 1) {
            initBackupFragment();
        } else if (position == 2) {
            initReminderSettingFragment();
        } else if (position == 3) {
            showLanguageDialog();//弹出语言选择对话框
        } else if (position == 4) {
            showWeekDialog(position);//弹出week选择对话框
        } else if (position == 5) {
            //图案锁
        } else if (position == 6) {

        } else if (position == 7) {

        } else if (position == 8) {
        } else if (position == 9) {
            showUnitDialog(position);
        } else if (position == 10) {
            showInputMethedDialog(position);
        }
    }

    /**
     * 初始化备份fragment
     */
    private void initBackupFragment() {
        if (backupFragment == null) {
            backupFragment = new BackupFragment();
        }
        recycleView.setVisibility(View.INVISIBLE);
        if (backupFragment.isAdded()) {
            FragmentUtils.showFragment(this, backupFragment);
        } else {
            FragmentUtils.addFragment(this, R.id.fm_setting_fragment_container, backupFragment, BackupFragment.class.getSimpleName());
        }
    }

    /**
     * 初始化提醒fragment
     */
    private void initReminderSettingFragment() {
        if (reminderSettingFragment == null) {
            reminderSettingFragment = new ReminderSettingFragment();
        }
        recycleView.setVisibility(View.INVISIBLE);
        if (reminderSettingFragment.isAdded()) {
            FragmentUtils.showFragment(this, reminderSettingFragment);
        } else {
            FragmentUtils.addFragment(this, R.id.fm_setting_fragment_container, reminderSettingFragment, ReminderSettingFragment.class.getSimpleName());
        }
    }

    /**
     * 语言选择对话框
     */
    private void showLanguageDialog() {
        final CustomDialog.CommonBuilder builder = new CustomDialog.CommonBuilder(this, getResources().getStringArray(R.array.language));
        builder.setOnLanguageDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                Toast.makeText(getApplicationContext(), "sdfsdf", Toast.LENGTH_LONG).show();
                // TODO: 2017/8/28
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.setTitle("选择语言");
        builder.create().show();
    }

    /**
     * 语言选择对话框
     */
    private void showWeekDialog(final int position) {
        final CustomDialog.CommonBuilder builder = new CustomDialog.CommonBuilder(this, getResources().getStringArray(R.array.week));
        builder.setOnLanguageDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                updateUI(position, str);
                // TODO: 2017/8/28
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.setTitle("一周的第一天");
        builder.create().show();
    }

    /**
     * 单位选择对话框
     */
    private void showUnitDialog(final int position) {
        final CustomDialog.UnitBuilder builder = new CustomDialog.UnitBuilder(this);
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                updateUI(position, str);
                //// TODO: 2017/8/28 保存数据

            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }

    /**
     * 输入方式选择对话框
     */
    private void showInputMethedDialog(final int position) {
        final CustomDialog.InputMethedBuilder builder = new CustomDialog.InputMethedBuilder(this);
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                updateUI(position, str);
                //// TODO: 2017/8/28 保存数据
            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        Display d = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = (int) (d.getHeight() * 0.2);
        lp.width = (int) (d.getWidth() * 0.65);
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    /**
     * 更新ui
     */
    private void updateUI(int position, String str) {
        int first = layoutManager.findFirstVisibleItemPosition();
        if (position - first >= 0) {
            View v = recycleView.getChildAt(position - first);
            RecyclerView.ViewHolder holder = recycleView.getChildViewHolder(v);
            if (holder instanceof RecycleViewAdapter.Common1ViewHolder) {
                ((RecycleViewAdapter.Common1ViewHolder) holder).tv_item2_content.setText(str);//刷新界面的数据
            }
        }
    }
}
