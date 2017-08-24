package com.xiaoxie.weightrecord.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * desc:基类fragment
 * Created by xiaoxie on 2017/8/23.
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    /**
     * 是否已经创建视图
     */
    protected boolean isViewCreated = false;
    /**
     * 是否已加载完数据
     */
    protected boolean isLoadDataCompleted = false;

    /**
     * 获取全局的activity 防止getActivity为空
     *
     * @param context 上下文
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    /**
     * 懒加载 只有当这个fragment可见的时候才去加载数据
     *
     * @param isVisibleToUser 是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //对于单个fragment是不会调用该方法的，只适用于ViewPager控件包裹的Fragment，当切换他们的可见性时会执行以下代码
        if (isVisibleToUser && isViewCreated && isLoadDataCompleted) {
            initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity).inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        isViewCreated = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //默认首先显示第一个Fragment，所以这个地方还是要调用一次，不然第一个Fragment不会加载数据
        if (getUserVisibleHint()) {
            initData();
            isLoadDataCompleted = true;
        }
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return 控件id
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 执行数据的加载
     */
    protected abstract void initData();
}
