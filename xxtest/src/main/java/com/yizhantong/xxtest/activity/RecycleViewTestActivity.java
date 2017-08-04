package com.yizhantong.xxtest.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.yizhantong.xxtest.R;
import com.yizhantong.xxtest.adapter.RecycleViewAdapter;
import com.yizhantong.xxtest.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewTestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager llayoutManager;
    private GridLayoutManager glayoutManager;
    private StaggeredGridLayoutManager slayoutManager;
    private RecycleViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_test);
        findViews();
        adapter = new RecycleViewAdapter(initData());
        llayoutManager = new LinearLayoutManager(this);//线性布局呈现
        glayoutManager = new GridLayoutManager(this, 5);//网格布局呈现
        slayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);//瀑布流布局呈现
        recyclerView.setLayoutManager(slayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, Color.GRAY));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecycleViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.deleteItems(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                adapter.updateItems();
            }
        });
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    /**
     * 下拉刷新
     */
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            adapter.updateItems();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
        }
    };


    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            list.add("" + (char) i);
        }
        return list;
    }
}
