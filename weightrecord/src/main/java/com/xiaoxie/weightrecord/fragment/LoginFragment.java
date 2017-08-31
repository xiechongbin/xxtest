package com.xiaoxie.weightrecord.fragment;

import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

/**
 * desc: 云备备份fragment
 * Created by xiaoxie on 2017/8/31.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private Button login_or_reister;
    private boolean isRegister;
    private TextView tvForgetPassword;

    private EditText editText_account;
    private EditText editText_password;
    private EditText editText_name;
    private ImageView imageViewHide;
    private boolean isshow = false;

    public static LoginFragment newInstance(boolean isRegister) {
        Bundle args = new Bundle();
        args.putBoolean("isRegister", isRegister);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_login_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        login_or_reister = view.findViewById(R.id.bt_login_or_register);
        editText_account = view.findViewById(R.id.edit_account);
        editText_password = view.findViewById(R.id.edit_password);
        editText_name = view.findViewById(R.id.edit_name);
        tvForgetPassword = view.findViewById(R.id.tv_forget_password);
        imageViewHide = view.findViewById(R.id.img_showPassword);
    }

    @Override
    protected void initData() {
        login_or_reister.setOnClickListener(this);
        isRegister = getArguments().getBoolean("isRegister");
        Log.d("isRegister",isRegister+"");
        if (isRegister) {
            editText_name.setVisibility(View.VISIBLE);
            tvForgetPassword.setVisibility(View.GONE);
            login_or_reister.setText(R.string.label_signup);
        } else {
            login_or_reister.setText(R.string.common_signin_button_text);
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login_or_register:
                break;
            case R.id.img_showPassword:
                if(!isshow){
                    editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                isshow = !isshow;
                break;
        }
    }
}
