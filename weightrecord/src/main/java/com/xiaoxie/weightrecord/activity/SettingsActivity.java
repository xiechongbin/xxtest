package com.xiaoxie.weightrecord.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.adapter.RecycleViewAdapter;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.view.ActionbarView;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;

public class SettingsActivity extends AppCompatActivity implements OnItemClickListener{
    private RecyclerView recycleView;
    private RecycleViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView = (RecyclerView) findViewById(R.id.setting_recycleView);
        recycleView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,2,getColor(R.color.color_f2f2f2)));
        recycleView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(this);
        adapter.setOnItemClickListener(this);
        recycleView.setAdapter(adapter);
        setCustomActionBar();
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
    public void onItemClick(int position,int id) {
        Toast.makeText(this,"position = "+position+">>id = "+id,Toast.LENGTH_SHORT).show();
    }
}
