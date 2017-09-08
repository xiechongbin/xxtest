package com.xiaoxie.weightrecord.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.adapter.AddDataRecycleViewAdapter;
import com.xiaoxie.weightrecord.bean.Options;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener1;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.realm.RealmStorageHelper;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;

import java.util.HashMap;

/**
 * desc:新增数据的fragment
 * Created by xiaoxie on 2017/9/7.
 */
public class AddDataFragment extends BaseFragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private AddDataRecycleViewAdapter addDataRecycleViewAdapter;
    private Activity activity;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_add_data_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.add_data_recyclerView);

    }

    @Override
    protected void initData() {
        this.activity = getActivity();
        Options options = RealmStorageHelper.getInstance().getOptions().get(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL, 2, Utils.getColor(activity, R.color.color_f2f2f2), true));
        recyclerView.setLayoutManager(layoutManager);
        addDataRecycleViewAdapter = new AddDataRecycleViewAdapter(activity, options);
        addDataRecycleViewAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(addDataRecycleViewAdapter);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onItemClick(int position, int id) {
        if (position == 0) {
            showDateResetDialog(0);
        }
        if (position == addDataRecycleViewAdapter.getItemCount() - 1) {
            showMoreChoiceDialog();
        }
    }

    private void showDateResetDialog(final int pos) {
        final CustomDialog.DateBuilder builder = new CustomDialog.DateBuilder(activity);
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                addDataRecycleViewAdapter.updateInputContent(str, pos);
            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 更多选择展示对话框
     */
    private void showMoreChoiceDialog() {
        final CustomDialog.BodyDataChoiceBuilder builder = new CustomDialog.BodyDataChoiceBuilder(activity);
        builder.setOnDialogClickListener(new DialogClickListener1() {
            @Override
            public void OnConfirmed(Object obj) {
                if (obj == null) {
                    return;
                }
                addDataRecycleViewAdapter.updateOptions((Options) obj);
                addDataRecycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnCanceled() {
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}
