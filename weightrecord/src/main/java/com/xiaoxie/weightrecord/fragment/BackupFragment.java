package com.xiaoxie.weightrecord.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoxie.weightrecord.R;

/**
 * 设置界面备份导入导出fragment
 * Created by gt on 2017/8/25.
 */

public class BackupFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_export_backup;//导出至备份文件
    private TextView tv_import_backup;//从备份文件导入
    private TextView tv_export_to_excel;//导出到execl

    @Override
    protected int getLayoutId() {
        return R.layout.frag_backup_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tv_export_backup = view.findViewById(R.id.tv_export_backup);
        tv_import_backup = view.findViewById(R.id.tv_import_backup);
        tv_export_to_excel = view.findViewById(R.id.tv_export_to_excel);
    }

    @Override
    protected void initData() {
        tv_export_backup.setOnClickListener(this);
        tv_import_backup.setOnClickListener(this);
        tv_export_to_excel.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_export_backup:
                //// TODO: 2017/8/28
                break;
            case R.id.tv_import_backup:

                break;
            case R.id.tv_export_to_excel:

                break;
        }
    }
}
