package com.xiaoxie.weightrecord.adapter;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:年份选择
 * Created by xiaoxie on 2017/8/18.
 */
public class YearSelectAdapter extends BaseAdapter {
    private List<Integer> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public YearSelectAdapter(Context context) {
        this.context = context;
        initData();
        inflater = LayoutInflater.from(context);
    }

    private void initData() {
        if (list == null) {
            return;
        }
        list.clear();
        for (int i = 1; i < 10000; i++) {
            list.add(i);
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            Log.d("weightrecode", "position = " + position);
            convertView = inflater.inflate(R.layout.layout_years_litems, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_items_year);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(String.valueOf(list.get(position)));
        holder.textView.setTextSize(30);
        return convertView;
    }

    public class ViewHolder {
        TextView textView;
    }
}
