package com.xiaoxie.weightrecord.adapter;

import android.content.Context;
import android.net.wifi.aware.PublishConfig;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

import java.util.HashMap;

/**
 * desc:
 * Created by xiaoxie on 2017/8/18.
 */
public class CommonAdapter extends BaseAdapter {
    private String[] array;
    private LayoutInflater inflater;
    private radioButtonClickCallback callback;

    private HashMap<String, Boolean> states = new HashMap<>();//用于记录每个RadioButton的状态，并保证只可选一个

    private int index;

    public CommonAdapter(Context context, String[] array) {
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_common_item, null);
            holder.textView = convertView.findViewById(R.id.tv_common_item);
            holder.radioButton = convertView.findViewById(R.id.rb_select);
            holder.radioButton.setChecked(false);
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callback != null) {
                        callback.onRadioButtonClick(holder.textView.getText().toString());
                        //重置，确保最多只有一项被选中
                        for (String key : states.keySet()) {
                            states.put(key, false);

                        }
                        states.put(String.valueOf(position), holder.radioButton.isChecked());
                        CommonAdapter.this.notifyDataSetChanged();
                    }

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(array[position]);
        boolean res;
        if (states.get(String.valueOf(position)) == null || !states.get(String.valueOf(position))) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;

        holder.radioButton.setChecked(res);
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
        RadioButton radioButton;
    }

    public void setCallback(radioButtonClickCallback callback) {
        this.callback = callback;
    }

    public interface radioButtonClickCallback {
        void onRadioButtonClick(String str);
    }
}
