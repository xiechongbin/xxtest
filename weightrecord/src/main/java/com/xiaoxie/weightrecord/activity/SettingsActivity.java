package com.xiaoxie.weightrecord.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.utils.FragmentUtils;
import com.xiaoxie.weightrecord.adapter.RecycleViewAdapter;
import com.xiaoxie.weightrecord.fragment.BackupFragment;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.view.ActionbarView;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;

public class SettingsActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView recycleView;
    private RecycleViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    private BackupFragment backupFragment;

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
}
