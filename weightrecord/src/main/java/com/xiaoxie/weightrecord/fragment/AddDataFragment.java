package com.xiaoxie.weightrecord.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.adapter.AddDataRecycleViewAdapter;
import com.xiaoxie.weightrecord.bean.BodyData;
import com.xiaoxie.weightrecord.bean.Options;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener1;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.realm.RealmStorageHelper;
import com.xiaoxie.weightrecord.utils.FragmentUtils;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;

/**
 * desc:新增数据的fragment
 * Created by xiaoxie on 2017/9/7.
 */
public class AddDataFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
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
        Button save = view.findViewById(R.id.bt_data_save);
        Button delete = view.findViewById(R.id.bt_data_delete);
        save.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        this.activity = getActivity();
        BodyData bodyData = new BodyData();
        Options options = RealmStorageHelper.getInstance().getOptions().get(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL, 2, Utils.getColor(activity, R.color.color_f2f2f2), true));
        recyclerView.setLayoutManager(layoutManager);
        addDataRecycleViewAdapter = new AddDataRecycleViewAdapter(activity, options, bodyData);
        addDataRecycleViewAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(addDataRecycleViewAdapter);

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onItemClick(int position, int id) {
        String title = addDataRecycleViewAdapter.getTitleWithPosition(position);
        if (position == 0) {
            showDateResetDialog(0);
        }
        if (position == addDataRecycleViewAdapter.getItemCount() - 1) {
            showMoreChoiceDialog();
        }
        if (TextUtils.isEmpty(title)) {
            return;
        }
        if (title.contains("体重")) {
            showWeightInputDialog(position, "kg");
        } else if (title.equals("内脏脂肪") || title.equals("筋肉") ||
                title.equals("骨头") || title.equals("身体水分") ||
                title.equals("心率") || title.equals("二头肌") ||
                title.equals("颈部") || title.equals("腰") ||
                title.equals("腕") || title.equals("臀部") ||
                title.equals("前臂") || title.equals("胸围") ||
                title.equals("胸部") || title.equals("腹部") ||
                title.equals("大腿")) {
            showWeightInputDialog(position, "");
        } else if (title.equals("体脂")) {
            showBodyFatInputMethodDialog(position);
        } else if (title.equals("BMR")) {
            showBMRInputMethodDialog(position);
        } else if (title.equals("饮食") || title.equals("活动")) {
            // TODO: 2017/9/13  
        }
    }

    /**
     * 日期选择对话框
     */
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

    /**
     * 体重输入对话框
     */
    private void showWeightInputDialog(final int pos, String unit) {
        CustomDialog.WeightAndHeightBuilder builder = new CustomDialog.WeightAndHeightBuilder(activity, unit);
        builder.setOnWeightDialogOnclickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                addDataRecycleViewAdapter.updateInputContent(str, pos);
            }

            @Override
            public void OnCanceled() {

            }
        });
        builder.create().show();

    }

    /**
     * 体脂输入方式
     */
    private void showBodyFatInputMethodDialog(final int pos) {
        CustomDialog.BodyFatBuilder builder = new CustomDialog.BodyFatBuilder(activity);
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                if (TextUtils.isEmpty(str))
                    return;
                if (str.equals(String.valueOf(R.id.ll_input_direct))) {
                    showWeightInputDialog(pos, "");
                } else if (str.equals(String.valueOf(R.id.ll_input_from_bmi))) {

                } else if (str.equals(String.valueOf(R.id.ll_input_from_body_fat))) {

                }
            }

            @Override
            public void OnCanceled() {
            }
        });
        Utils.setCostumeDialogStyle(builder.create(), activity, 1f, 0.3f, 0, 0, Gravity.BOTTOM, 0).show();
    }

    /**
     * BMR输入方式
     */
    private void showBMRInputMethodDialog(final int pos) {
        CustomDialog.BMRBuilder builder = new CustomDialog.BMRBuilder(activity);
        builder.setOnDialogClickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String str) {
                if (TextUtils.isEmpty(str))
                    return;
                if (str.equals(String.valueOf(R.id.ll_input_bmr_direct))) {
                    showWeightInputDialog(pos, "");
                } else if (str.equals(String.valueOf(R.id.ll_input_bmr_from_auto_calculate))) {

                }
            }

            @Override
            public void OnCanceled() {
            }
        });
        Utils.setCostumeDialogStyle(builder.create(), activity, 1f, 0.2f, 0, 0, Gravity.BOTTOM, 0).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_data_save:
                saveData();
                FragmentUtils.removeFragment(getActivity(), this);
                break;
            case R.id.bt_data_delete:
                FragmentUtils.removeFragment(getActivity(), this);
                break;
        }
    }

    private void saveData() {
        BodyData bodyData = addDataRecycleViewAdapter.getBodyData();
        RealmStorageHelper.getInstance().insertBodyData(bodyData);
    }
}
