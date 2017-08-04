package com.yizhantong.xxtest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yizhantong.xxtest.R;

/**
 * desc:上拉 下拉 刷星自定义控件
 * Created by xiaoxie on 2017/8/4.
 */
public class PullLoadMoreRecycleView extends LinearLayout {
    private Context context;
    private SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新控件
    private RecyclerView mRecyclerView;
    private View mFooterView;
    private TextView loadMoreText;
    private LinearLayout loadMoreLayout;

    private PullLoadMoreListener mPullLoadMoreListener;

    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private boolean pullRefreshEnable = true;
    private boolean pushRefreshEnable = true;


    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(!isRefresh()){
                setIsRefresh(true);
                refresh();
            }
        }
    };//下拉刷新监听

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastItem = 0;
            int firstItem = 0;
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
                firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItem == -1) {
                    lastItem = gridLayoutManager.findLastVisibleItemPosition();
                }
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
                firstItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItem == -1) {
                    lastItem = linearLayoutManager.findLastVisibleItemPosition();
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
                lastItem = findMax(lastPositions);
                firstItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
            }

            if (firstItem == 0 || firstItem == RecyclerView.NO_POSITION) {
                if (getPullRefreshEnable()) {
                    setSwipeRefreshEnable(true);
                } else {
                    setSwipeRefreshEnable(false);
                }
            }
            if (getPushRefreshEnable() && !isRefresh() && isHasMore() && (lastItem == totalItemCount - 1) && !isLoadMore() && (dx > 0 || dy > 0)) {
                setIsLoadMore(true);
                loadMore();
            }

        }
    };//滑动监听

    /**
     * 构造方法
     */
    public PullLoadMoreRecycleView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadMoreRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullLoadMoreRecycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PullLoadMoreRecycleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 初始化控件
     */
    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pull_load_more, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(onScrollListener);
        mRecyclerView.setOnTouchListener(new onTouchRecyclerView());

        mFooterView = view.findViewById(R.id.footerView);
        loadMoreLayout = (LinearLayout) view.findViewById(R.id.loadMoreLayout);
        loadMoreText = (TextView) view.findViewById(R.id.loadMoreText);

        mFooterView.setVisibility(View.GONE);
        this.addView(view);
    }

    /**
     * 线性布局管理器
     */
    public void setLinearLayout(int orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(orientation);//设置方向
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * 网格布局管理器
     */
    public void setGridLayout(int spanCount, int orientation) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        gridLayoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * 瀑布流
     */
    public void setStaggeredGridLayout(int spanCount, int orientation) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, orientation);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    /**
     * 获取LayoutManager
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    /**
     * 获取recycleview
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 设置动画
     */
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    /**
     * 设置分割线
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        mRecyclerView.addItemDecoration(decor, index);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void addItemDecoration(RecycleViewDivider divider) {
        mRecyclerView.addItemDecoration(divider);
    }

    /**
     * 上划置顶部
     */
    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 设置适配器
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    /**
     * 设置是否允许上拉刷新
     */
    public void setPullRefreshEnable(boolean enable) {
        pullRefreshEnable = enable;
        setSwipeRefreshEnable(enable);
    }

    /**
     * 获取是否允许上拉刷新
     */
    public boolean getPullRefreshEnable() {
        return pullRefreshEnable;
    }

    private void setSwipeRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    /**
     * 获取是否允许下拉刷新
     */
    public boolean getSwipeRefreshEnable() {
        return mSwipeRefreshLayout.isEnabled();
    }

    /**
     * 设置下拉刷新颜色
     */
    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    /**
     * 获取下拉刷新控件
     */
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    /**
     * 设置刷新状态
     */
    public void setRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (pullRefreshEnable)
                    mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });
    }

    /**
     * 获取刷新状态
     */
    public boolean getPushRefreshEnable() {
        return pushRefreshEnable;
    }

    /**
     * 设置刷新状态
     */
    public void setPushRefreshEnable(boolean pushRefreshEnable) {
        this.pushRefreshEnable = pushRefreshEnable;
    }

    /**
     * *****************************footView 相关方法*************************
     */

    /**
     * 获取footView实例
     */
    public LinearLayout getFooterViewLayout() {
        return loadMoreLayout;
    }

    /**
     * 设置背景颜色
     */
    public void setFooterViewBackgroundColor(int color) {
        loadMoreLayout.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    /**
     * 设置文字
     */
    public void setFooterViewText(CharSequence text) {
        loadMoreText.setText(text);
    }

    /**
     * 设置文字
     */
    public void setFooterViewText(int resid) {
        loadMoreText.setText(resid);
    }

    /**
     * 设置文字颜色
     */
    public void setFooterViewTextColor(int color) {
        loadMoreText.setTextColor(ContextCompat.getColor(context, color));
    }

    /**
     * 刷新
     */
    public void refresh() {
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        if (mPullLoadMoreListener != null && hasMore) {
            mFooterView.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mFooterView.setVisibility(View.VISIBLE);
                        }
                    })
                    .start();
            invalidate();
            mPullLoadMoreListener.onLoadMore();

        }
    }

    /**
     * 加载完成
     */
    public void setPullLoadMoreCompleted() {
        isRefresh = false;
        setRefreshing(false);

        isLoadMore = false;
        mFooterView.animate()
                .translationY(mFooterView.getHeight())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

    }

    /**
     * 设置回调监听
     */
    public void setmPullLoadMoreListener(PullLoadMoreListener loadMoreListener) {
        this.mPullLoadMoreListener = loadMoreListener;

    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     * 寻找最大的值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 解决数组越界问题
     */
    class onTouchRecyclerView implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return isRefresh || isLoadMore;
        }
    }

    /**
     * 上拉加载更多回调
     */
    public interface PullLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }
}
