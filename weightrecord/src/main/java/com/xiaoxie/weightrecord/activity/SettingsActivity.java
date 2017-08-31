package com.xiaoxie.weightrecord.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.adapter.RecycleViewAdapter;
import com.xiaoxie.weightrecord.fragment.BackupFragment;
import com.xiaoxie.weightrecord.fragment.BaseFragment;
import com.xiaoxie.weightrecord.fragment.ReminderSettingFragment;
import com.xiaoxie.weightrecord.interfaces.BackHandledInterface;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.utils.FragmentUtils;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.ActionbarView;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;

public class SettingsActivity extends AppCompatActivity implements OnItemClickListener, BackHandledInterface {
    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private BackupFragment backupFragment;
    private ReminderSettingFragment reminderSettingFragment;
    private boolean hasPassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hasPassword = SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_HAS_PASSWORD, false);
        initRecycleView();
        setCustomActionBar();
        context = this;
    }

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView = (RecyclerView) findViewById(R.id.setting_recycleView);
        recycleView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getColor(R.color.color_f2f2f2)));
        recycleView.setLayoutManager(layoutManager);
        RecycleViewAdapter adapter = new RecycleViewAdapter(this);
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
        if (actionBar == null) {
            return;
        }
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
        } else if (position == 5) {//图案锁
            initLockView();
        } else if (position == 6) {
            boolean state = SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_EXPORT_BMI_AUTO, false);
            SharePrefenceUtils.setBoolean(this, SharePrefenceUtils.KEY_EXPORT_BMI_AUTO, !state);
            UpdateToggleButtonState(position, !state);
        } else if (position == 7) {
            boolean state = SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_EXPORT_BMR_AUTO, false);
            SharePrefenceUtils.setBoolean(this, SharePrefenceUtils.KEY_EXPORT_BMR_AUTO, !state);
            UpdateToggleButtonState(position, !state);
        } else if (position == 8) {
            boolean state = SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_AUDIO, false);
            SharePrefenceUtils.setBoolean(this, SharePrefenceUtils.KEY_AUDIO, !state);
            UpdateToggleButtonState(position, !state);
        } else if (position == 9) {
            showUnitDialog(position);
        } else if (position == 10) {
            showInputTypeDialog(position);
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
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_LANGUAGE, str);
                Utils.setCurrentLanguage(str, context);
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
                updateUI(position, str);
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_FIRST_DAY_OF_WEEK, str);
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
                if (str.equals("null")) {
                    Toast.makeText(context, "选项不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    updateUI(position, str);
                    String weightUnit = str.substring(0, str.indexOf(","));
                    String heightUnit = str.substring(str.indexOf(","), str.length());
                    SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_HEIGHT_UNIT, heightUnit);
                    SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_WEIGHT_UNIT, weightUnit);
                }
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
    private void showInputTypeDialog(final int position) {
        final CustomDialog.InputTypeBuilder builder = new CustomDialog.InputTypeBuilder(this);
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                updateUI(position, str);
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_WEIGHT_INPUT_TYPE, str);
            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        Display d = getWindowManager().getDefaultDisplay();
        if (dialogWindow == null) {
            return;
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = (int) (d.getHeight() * 0.2);
        lp.width = (int) (d.getWidth() * 0.65);
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    /**
     * 设置锁屏密码
     */
    private void initLockView() {
        hasPassword = SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_HAS_PASSWORD, false);
        if (hasPassword) {
            UpdateToggleButtonState(5, false);
            SharePrefenceUtils.setBoolean(this, SharePrefenceUtils.KEY_HAS_PASSWORD, false);
            SharePrefenceUtils.setString(this, SharePrefenceUtils.KEY_PASSWORD, "");//制空
        } else {
            Intent it = new Intent();
            it.putExtra("isFromSetting", true);
            it.setClass(SettingsActivity.this, LockPatternActivity.class);
            startActivity(it);
        }

    }

    /**
     * 更新ui
     */
    private void updateUI(int position, String str) {
        int first = layoutManager.findFirstVisibleItemPosition();
        if (position - first >= 0) {
            View v = recycleView.getChildAt(position - first);
            if (v == null) {
                return;
            }
            RecyclerView.ViewHolder holder = recycleView.getChildViewHolder(v);
            if (holder instanceof RecycleViewAdapter.Common1ViewHolder) {
                ((RecycleViewAdapter.Common1ViewHolder) holder).tv_item2_content.setText(str);//刷新界面的数据
            }
        }
    }

    private void UpdateToggleButtonState(int position, boolean state) {
        int first = layoutManager.findFirstVisibleItemPosition();
        if (position - first >= 0) {
            View v = recycleView.getChildAt(position - first);
            if (v == null) {
                return;
            }
            RecyclerView.ViewHolder holder = recycleView.getChildViewHolder(v);
            if (holder instanceof RecycleViewAdapter.SwitchViewHolder) {
                ((RecycleViewAdapter.SwitchViewHolder) holder).toggleButton.setBackgroundResource(state ? R.drawable.switch_on : R.drawable.switch_off);//刷新界面的数据
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateToggleButtonState(5, SharePrefenceUtils.getBoolean(this, SharePrefenceUtils.KEY_HAS_PASSWORD, false));
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }
}
