package com.xiaoxie.weightrecord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

/**
 * desc:
 * Created by xiaoxie on 2017/8/18.
 */
public class CommonAdapter extends BaseAdapter {
    private String[] array;
    private Context context;
    private LayoutInflater inflater;

    public CommonAdapter(Context context, String[] array) {
        this.context = context;
        this.array = array;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int position) {
        return array[position];
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
            convertView = inflater.inflate(R.layout.layout_common_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_common_item);
            holder.radioButton = (RadioButton) convertView.findViewById(R.id.rb_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(array[position]);
        return convertView;
    }

    public class ViewHolder {
        TextView textView;
        RadioButton radioButton;
    }
}
