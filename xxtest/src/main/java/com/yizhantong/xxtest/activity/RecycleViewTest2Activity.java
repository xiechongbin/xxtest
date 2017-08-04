package com.yizhantong.xxtest.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yizhantong.xxtest.R;
import com.yizhantong.xxtest.adapter.RecycleViewAdapter;
import com.yizhantong.xxtest.view.PullLoadMoreRecycleView;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义控件实现上拉下拉
 */
public class RecycleViewTest2Activity extends AppCompatActivity {

    private PullLoadMoreRecycleView mPullLoadMoreRecyclerView;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter adapter;
    private Context context;

    private int mCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_test2);
        context = this;
        initView();
    }

    private void initView() {
        mPullLoadMoreRecyclerView = (PullLoadMoreRecycleView) findViewById(R.id.pullLoadMoreRecycleView);
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();//获取recycle对象
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout(LinearLayout.VERTICAL);

        mPullLoadMoreRecyclerView.setmPullLoadMoreListener(new PullLoadMoreRecycleView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(context, "onrefresh", Toast.LENGTH_SHORT).show();
                setRefresh();
                getData();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(context, "onLoadMore", Toast.LENGTH_SHORT).show();
                mCount = mCount + 1;
                getData();
            }
        });
        adapter = new RecycleViewAdapter(setList());
        mPullLoadMoreRecyclerView.setAdapter(adapter);
        getData();
    }

    private List<String> setList() {
        List<String> dataList = new ArrayList<>();
        int start = 20 * (mCount - 1);
        for (int i = start; i < 20 * mCount; i++) {
            dataList.add("Frist" + i);
        }
        return dataList;
    }
    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addAllData(setList());
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });

            }
        }, 1000);

    }
    public void clearData() {
        adapter.clearData();
        adapter.notifyDataSetChanged();
    }
    private void setRefresh() {
        adapter.clearData();
        mCount = 1;
    }
}
