package com.xiaoxie.weightrecord.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.utils.FragmentUtils;

/**
 * desc: 云备备份fragment
 * Created by xiaoxie on 2017/8/31.
 */
public class CloudFragment extends BaseFragment implements View.OnClickListener {
    private Button login;
    private Button register;
    private LoginFragment loginFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_cloud_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        login = view.findViewById(R.id.bt_login);
        register = view.findViewById(R.id.bt_register);
    }

    @Override
    protected void initData() {
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                showCloudFragment(false);
                break;
            case R.id.bt_register:
                showCloudFragment(true);
                break;
        }
    }

    private void showCloudFragment(boolean isRegister) {
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance(isRegister);
        }
        if (loginFragment.isAdded()) {
            FragmentUtils.showFragment(getActivity(), loginFragment);
        } else {
            FragmentUtils.addFragment(getActivity(), R.id.fm_fragment_container_hole, loginFragment, LoginFragment.class.getSimpleName());
        }
    }
}
